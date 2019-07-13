package com.netease.music.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.netease.music.dao.mapper.PlayListPOMapperExt;
import com.netease.music.dao.po.PlayListPO;
import com.netease.music.dao.po.PlayListPOExample;
import com.netease.music.entity.bo.PlayListDetailBO;
import com.netease.music.entity.constant.CrawlerConstant;
import com.netease.music.entity.constant.LogConstant;
import com.netease.music.entity.enums.CrawlingStatusEnum;
import com.netease.music.entity.enums.PageTypeEnum;
import com.netease.music.service.CrawlerService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CrawlerServiceImpl implements CrawlerService {
    @Autowired
    private PlayListPOMapperExt PlayListPOMapper;

    private static final ThreadPoolExecutor crawlerPlayListExecutor = new ThreadPoolExecutor(4, 8, 30, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1000),
            new ThreadFactoryBuilder().setNameFormat("crawlerPlayListExecutor-%d").build(),
            new RejectedExecutionHandler() {
                @Override
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    LogConstant.BUS.error("crawlerPlayListExecutor reject execute.");
                }
            });

    @Override
    @Async
    public void initCrawling() throws InterruptedException {
        // 获取全部歌单类别
        List<String> categoryNameList = getAllCategoryNames();
        LogConstant.BUS.info("all categories: {}.", JSON.toJSONString(categoryNameList));

        // 遍历每一个类别下的所有歌单
        if (CollectionUtils.isEmpty(categoryNameList)) {
            LogConstant.BUS.error("categoryNameList is empty.");
            return;
        }

        int categoryCount = categoryNameList.size();
        CountDownLatch countDownLatch = new CountDownLatch(categoryCount);
        for (String categoryName : categoryNameList) {
            // 获取分类下的所有id
            crawlerPlayListExecutor.execute(() -> {
                try {
                    LogConstant.BUS.info("start... init category {}.", categoryName);
                    initPlayListOneCategory(categoryName);
                    LogConstant.BUS.info("end... init category {} end with success.", categoryName);
                } catch (Exception e) {
                    LogConstant.BUS.error("init category {} failed, exception info:{}.", categoryName, e.getMessage(), e);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            LogConstant.BUS.error("initCrawling InterruptedException:", e);
            throw new InterruptedException();
        }
    }

    @Override
    public void initPlayListOneCategory(String categoryName) {
        if (StringUtils.isBlank(categoryName)) {
            LogConstant.BUS.error("crawlingWithCategory failed, param categoryName cannot be blank.");
            return;
        }

        int offset = 0;
        int limit = CrawlerConstant.DEFAULT_PAGE_SIZE;

        for (;;) {
            // 歌单页url
            String playListsUrl = CrawlerConstant.getPlayListsUrl(categoryName, limit, offset);
            if (StringUtils.isBlank(playListsUrl)) {
                LogConstant.BUS.error("crawlingWithCategory failed, playListsUrl is blank, categoryName={}.",
                        categoryName);
                return;
            }

            LogConstant.BUS.info("start getPlayListIdOneCategory, category {}, page {}.", categoryName,
                    offset / 35 + 1);
            try {
                List<PlayListDetailBO> playListDetailBOList = getPlayListOnePage(playListsUrl);
                if (CollectionUtils.isEmpty(playListDetailBOList)) {
                    LogConstant.BUS.info("playListDetailBOList is empty, category {}, page {}.", categoryName,
                            offset / 35 + 1);
                    break;
                }

                // 插入数据库
                for (PlayListDetailBO playListDetailBO : playListDetailBOList) {
                    PlayListPO PlayListPO = new PlayListPO();
                    BeanUtils.copyProperties(playListDetailBO, PlayListPO);
                    PlayListPO.setCrawlingStatus(CrawlingStatusEnum.UN_CRAWLERED);

                    PlayListPOExample example = new PlayListPOExample();
                    example.createCriteria().andResourceIdEqualTo(playListDetailBO.getResourceId());
                    int existsPlayListCount = PlayListPOMapper.countByExample(example);
                    if (existsPlayListCount == 0) {
                        PlayListPOMapper.insertSelective(PlayListPO);
                    }
                }

                LogConstant.BUS.info("getPlayListIdOneCategory success, category {}, page {}.", categoryName,
                        limit / 35 + 1);
            } catch (Exception e) {
                LogConstant.BUS.error("getPlayListIdOneCategory failed, category {}, page {}.", categoryName,
                        limit / 35 + 1);
            }
            offset += limit;
        }
    }

    @Override
    public List<PlayListDetailBO> getPlayListOnePage(String url) throws Exception {
        Document doc = Jsoup.connect(url).get();
        if (doc == null) {
            LogConstant.BUS.info("doc is null, url={}.", url);
            return null;
        }

        Elements playListElements = doc.select("ul#m-pl-container");
        if (playListElements == null || playListElements.size() == 0) {
            LogConstant.BUS.info("playListElements is null, url={}.", url);
            return null;
        }

        Element playListElement = playListElements.first();
        if (playListElement == null) {
            LogConstant.BUS.info("playListElement is null, url={}.", url);
            return null;
        }

        List<PlayListDetailBO> playListDetailBOList = new ArrayList<>();
        Pattern playListPattern = Pattern.compile("^(/playlist\\?id=)(\\d+)$");
        Pattern publisherPattern = Pattern.compile("^(/user/home\\?id=)(\\d+)$");

        Elements playListBlockElements = playListElement.getElementsByTag("li");
        for (Element playListBlockElement : playListBlockElements) {
            PlayListDetailBO playListDetailBO = new PlayListDetailBO();

            // 填充歌单信息
            Element playListUrlElement = playListBlockElement.getElementsByClass("msk").first();
            String playListUrl = playListUrlElement.attr("href");
            String title = playListUrlElement.attr("title");
            if (StringUtils.isBlank(playListUrl)) {
                continue;
            }

            Matcher playListMatcher = playListPattern.matcher(playListUrl);
            if (playListMatcher.find()) {

                playListDetailBO.setUrl(playListUrl);
                playListDetailBO.setTitle(title);
                String playListIdString = playListMatcher.group(2);
                if (StringUtils.isNotBlank(playListIdString)) {
                    Long playListId = Long.valueOf(playListIdString);
                    playListDetailBO.setResourceId(playListId);
                }
            }

            // 填充发布者信息
            Element publisherElement = playListBlockElement.getElementsByClass("nm").first();
            String userUrl = publisherElement.attr("href");
            Matcher publisherMatcher = publisherPattern.matcher(playListUrl);
            if (publisherMatcher.find()) {
                String userIdString = publisherMatcher.group(2);
                if(StringUtils.isNotBlank(userIdString)) {
                    playListDetailBO.setCreateUserId(Long.valueOf(userIdString));
                }
            }
            String userName = publisherElement.attr("title");
            playListDetailBO.setCreateUserName(userName);

            playListDetailBOList.add(playListDetailBO);
        }

        return playListDetailBOList;
    }

    /**
     * 获取所有类别
     * 
     * @return
     */
    private List<String> getAllCategoryNames() {
        List<String> categoryNameList = new ArrayList<>();

        String startUrl = CrawlerConstant.START_URL;
        Document doc = null;
        try {
            doc = Jsoup.connect(startUrl).get();
        } catch (IOException e) {
            LogConstant.BUS.error("cannot read url:{}.", startUrl, e);
        }

        if (doc == null) {
            LogConstant.BUS.info("document of url {} is null.", startUrl);
            return null;
        }

        Elements categoryElements = doc.select("div#cateListBox");
        if (categoryElements == null) {
            LogConstant.BUS.info("categoryElements is null.");
            return null;
        }

        Element categoryElement = categoryElements.first();
        if (categoryElement == null) {
            LogConstant.BUS.info("categoryElement is null.");
            return null;
        }

        Elements categoryUrlElements = categoryElement.select("a[href]");
        for (Element categoryUrlElement : categoryUrlElements) {
            String categoryUrl = categoryUrlElement.attr("href");
            if (StringUtils.isNotBlank(categoryUrl) && categoryUrl.startsWith("/discover/playlist/")) {
                String categoryName = categoryUrlElement.attr("data-cat");
                if (StringUtils.isNotBlank(categoryName)) {
                    categoryNameList.add(categoryName);
                }
            }
        }

        return categoryNameList;
    }

    @Override
    public PlayListDetailBO getPlayListDetail(String url) {
        Document doc = null;

        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            LogConstant.BUS.error("jsoup connect url {} failed:{}.", url, e.getMessage(), e);
        }

        if (doc == null) {
            LogConstant.BUS.error("doc for url {} is null.", url);
            return null;
        }

        Elements playListElements = doc.select("div#m-playlist");
        if (playListElements == null || playListElements.size() == 0) {
            return null;
        }

        Element playListElement = playListElements.first();
//        playListElement.getElementsByClass()
return null;
    }
}
