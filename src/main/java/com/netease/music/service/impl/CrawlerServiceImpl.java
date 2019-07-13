package com.netease.music.service.impl;

import com.alibaba.fastjson.JSON;
import com.netease.music.dao.mapper.MusicPagePOMapperExt;
import com.netease.music.dao.po.MusicPagePO;
import com.netease.music.entity.bo.PlayListDetailBO;
import com.netease.music.entity.constant.CrawlerConstant;
import com.netease.music.entity.constant.LogConstant;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CrawlerServiceImpl implements CrawlerService {
    @Autowired
    private MusicPagePOMapperExt musicPagePOMapper;

    @Override
    @Async
    public void initCrawling() {
        // 获取全部歌单类别
        List<String> categoryNameList = getAllCategoryNames();
        LogConstant.BUS.info("all categories: {}.", JSON.toJSONString(categoryNameList));

        // 遍历每一个类别下的所有歌单
        if (CollectionUtils.isEmpty(categoryNameList)) {
            LogConstant.BUS.error("categoryNameList is empty.");
            return;
        }

        for (String categoryName : categoryNameList) {
            // 获取分类下的所有id
            initPlayListOneCategory(categoryName);

            // 写到数据库
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

            LogConstant.BUS.info("start getPlayListIdOneCategory, category {}, page {}.", categoryName, offset / 35 + 1);
            try {
                List<PlayListDetailBO> playListDetailBOList = getPlayListOnePage(playListsUrl);
                if (CollectionUtils.isEmpty(playListDetailBOList)) {
                    LogConstant.BUS.info("playListDetailBOList is empty, category {}, page {}.", categoryName, offset / 35 + 1);
                    break;
                }

                // 插入数据库
                for (PlayListDetailBO playListDetailBO : playListDetailBOList) {
                    MusicPagePO musicPagePO = new MusicPagePO();
                    BeanUtils.copyProperties(playListDetailBO, musicPagePO);
                    musicPagePOMapper.insertSelective(musicPagePO);
                }

                LogConstant.BUS.info("getPlayListIdOneCategory success, category {}, page {}.", categoryName, limit / 35 + 1);
            } catch (Exception e) {
                LogConstant.BUS.error("getPlayListIdOneCategory failed, category {}, page {}.", categoryName, limit / 35 + 1);
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

        Element playListElement = playListElements.get(0);
        if (playListElement == null) {
            LogConstant.BUS.info("playListElement is null, url={}.", url);
            return null;
        }

        List<PlayListDetailBO> playListDetailBOList = new ArrayList<>();
        Pattern p = Pattern.compile("^(/playlist\\?id=)(\\d+)$");
        Elements playListUrlElements = playListElement.getElementsByClass("msk");
        if (playListUrlElements == null || playListUrlElements.size() == 0) {
            LogConstant.BUS.info("playListUrlElements is null, url={}.", url);
            return null;
        }

        for (Element playListUrlElement : playListUrlElements) {
            String playListUrl = playListUrlElement.attr("href");
            if (StringUtils.isBlank(playListUrl)) {
                continue;
            }

            Matcher m = p.matcher(playListUrl);
            if (m.find()) {
                PlayListDetailBO playListDetailBO = new PlayListDetailBO();
                playListDetailBO.setUrl(playListUrl);
                String playListIdString = m.group(2);
                if (StringUtils.isNotBlank(playListIdString)) {
                    Long playListId = Long.valueOf(playListIdString);
                    playListDetailBO.setResourceId(playListId);
                }
                playListDetailBOList.add(playListDetailBO);
            }
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

        Element categoryElement = categoryElements.get(0);
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
}
