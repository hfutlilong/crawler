package com.netease.music.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.netease.kaola.cs.utils.DateUtil;
import com.netease.kaola.cs.utils.pagination.PaginationInfo;
import com.netease.music.dao.mapper.*;
import com.netease.music.dao.po.*;
import com.netease.music.entity.bo.PlayListDetailBO;
import com.netease.music.common.constant.CrawlerConstant;
import com.netease.music.common.log.LogConstant;
import com.netease.music.entity.enums.CrawlingStatusEnum;
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
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CrawlerServiceImpl implements CrawlerService {
    @Autowired
    private PlaylistCategoryPOMapperExt playlistCategoryPOMapperExt;

    @Autowired
    private PlayListPOMapperExt playListPOMapper;

    @Autowired
    private PlayListSongRelationPOMapperExt playListSongRelationPOMapper;

    @Autowired
    private SongPOMapperExt songPOMapperExt;

    @Autowired
    private UserInfoPOMapperExt userInfoPOMapper;

    private Lock playListCategoryLock = new ReentrantLock();
    private Lock playListLock = new ReentrantLock();
    private Lock songLock = new ReentrantLock();
    private Lock  userInfoLock = new ReentrantLock();

    private static final ThreadPoolExecutor crawlerExecutor = new ThreadPoolExecutor(4, 8, 30, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(5000),
            new ThreadFactoryBuilder().setNameFormat("crawlerPlayListExecutor-%d").build(),
            new RejectedExecutionHandler() {
                @Override
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    LogConstant.BUS.error("crawlerPlayListExecutor reject execute.");
                }
            });

    @Override
    @Async
    public void autoCrawling() throws InterruptedException {
        // 初始化歌单
        initCrawling();
//        // 发送初始化完成事件
//        CrawlerEventPublisher.publish(new InitPlayListFinishEvent());
        // 爬取歌单列表
        crawlingPlayList();
        // 爬取歌曲信息
        crawlingSongInfo();

        // 爬取歌单评论

        // 爬取歌曲评论
    }

    /**
     * 抓取所有歌单，存入数据库
     * 
     * @throws InterruptedException
     */
    public void initCrawling() throws InterruptedException {
        // 获取全部歌单类别
        List<String> categoryNameList = getAllCategoryNames();
        LogConstant.BUS.info("all categories: {}.", JSON.toJSONString(categoryNameList));

        if (CollectionUtils.isEmpty(categoryNameList)) {
            LogConstant.BUS.error("categoryNameList is empty.");
            return;
        }

        // 插入歌单分类表
        for (String categoryName : categoryNameList) {
            PlaylistCategoryPOExample playlistCategoryPOExample = new PlaylistCategoryPOExample();
            playlistCategoryPOExample.createCriteria().andCategoryNameEqualTo(categoryName);

            playListCategoryLock.lock();
            try {
                int duplicateCount = playlistCategoryPOMapperExt.countByExample(playlistCategoryPOExample);
                if (duplicateCount == 0) {
                    PlaylistCategoryPO playlistCategoryPO = new PlaylistCategoryPO();
                    playlistCategoryPO.setCategoryName(categoryName);
                    playlistCategoryPO.setCrawlingStatus(CrawlingStatusEnum.UN_CRAWLERED);
                    playlistCategoryPOMapperExt.insertSelective(playlistCategoryPO);
                }
            } catch (Exception e) {
                LogConstant.BUS.error("insert new playlistCategory failed, categoryName={}.", categoryName);
            } finally {
                playListCategoryLock.unlock();
            }
        }

        // 遍历每一个类别下的所有歌单
        int categoryCount = categoryNameList.size();
        CountDownLatch countDownLatch = new CountDownLatch(categoryCount);
        for (String categoryName : categoryNameList) {
            // 获取分类下的所有id
            crawlerExecutor.execute(() -> {
                try {
                    LogConstant.BUS.info("start... auto crawling category {}.", categoryName);
                    initPlayListOneCategory(categoryName);
                    LogConstant.BUS.info("end... init category {} end with success.", categoryName);
                } catch (Exception e) {
                    LogConstant.BUS.error("init category {} failed, exception info:{}.", categoryName, e.getMessage(),
                            e);
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

        LogConstant.BUS.info("initCrawling done.");
    }

    /**
     * 初始化一个类别的歌单
     * 
     * @param categoryName
     */
    @Override
    public void initPlayListOneCategory(String categoryName) {
        if (StringUtils.isBlank(categoryName)) {
            LogConstant.BUS.error("crawlingWithCategory failed, param categoryName cannot be blank.");
            return;
        }

        // 更新歌单分类表的爬取状态为爬取中
        updatePlayListCategoryCrawlingStatus(categoryName, CrawlingStatusEnum.CRAWLING);


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
                    updatePlayListCategoryCrawlingStatus(categoryName, CrawlingStatusEnum.CRAWLERED);
                    break;
                }

                // 插入数据库
                for (PlayListDetailBO playListDetailBO : playListDetailBOList) {
                    PlayListPO PlayListPO = new PlayListPO();
                    BeanUtils.copyProperties(playListDetailBO, PlayListPO);
                    PlayListPO.setCrawlingStatus(CrawlingStatusEnum.UN_CRAWLERED);

                    PlayListPOExample example = new PlayListPOExample();
                    example.createCriteria().andResourceIdEqualTo(playListDetailBO.getResourceId());

                    playListLock.lock();
                    try {
                        int existsPlayListCount = playListPOMapper.countByExample(example);
                        if (existsPlayListCount == 0) {
                            playListPOMapper.insertSelective(PlayListPO);
                        }
                    } finally {
                        playListLock.unlock();
                    }
                }

                LogConstant.BUS.info("getPlayListIdOneCategory success, category {}, page {}.", categoryName,
                        limit / 35 + 1);
            } catch (Exception e) {
                LogConstant.BUS.error("getPlayListIdOneCategory failed, category {}, page {}.", categoryName,
                        limit / 35 + 1);
                updatePlayListCategoryCrawlingStatus(categoryName, CrawlingStatusEnum.CRAWLING_FAILED);
                return;
            }
            offset += limit;
        }
    }

    /**
     * 更新歌单分类名表
     * @param categoryName
     * @param crawlingStatusEnum
     */
    private void updatePlayListCategoryCrawlingStatus(String categoryName, CrawlingStatusEnum crawlingStatusEnum) {
        PlaylistCategoryPO playlistCategoryPO = new PlaylistCategoryPO();
        playlistCategoryPO.setCategoryName(categoryName);
        playlistCategoryPO.setCrawlingStatus(crawlingStatusEnum);
        PlaylistCategoryPOExample playlistCategoryPOExample = new PlaylistCategoryPOExample();
        playlistCategoryPOExample.createCriteria().andCategoryNameEqualTo(categoryName);
        playlistCategoryPOMapperExt.updateByExampleSelective(playlistCategoryPO, playlistCategoryPOExample);
    }


    /**
     * 获取一个页面的所有歌单
     * 
     * @param url
     * @return
     * @throws Exception
     */
    private List<PlayListDetailBO> getPlayListOnePage(String url) throws Exception {
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
            Matcher publisherMatcher = publisherPattern.matcher(userUrl);
            if (publisherMatcher.find()) {
                String userIdString = publisherMatcher.group(2);
                if (StringUtils.isNotBlank(userIdString)) {
                    Long userId = Long.valueOf(userIdString);
                    playListDetailBO.setCreateUserId(userId);

                    // 更新用户信息
                    userInfoLock.lock();
                    try {
                        UserInfoPOExample userInfoPOExample = new UserInfoPOExample();
                        userInfoPOExample.createCriteria().andUserIdEqualTo(userId);
                        int duplicateCount = userInfoPOMapper.countByExample(userInfoPOExample);
                        if (duplicateCount == 0) {
                            UserInfoPO userInfoPO = new UserInfoPO();
                            userInfoPO.setUserId(userId);
                            userInfoPOMapper.insertSelective(userInfoPO);
                        }
                    } catch (Exception e) {
                        LogConstant.BUS.error("insert user info failed, userId={}.", userId);
                    } finally {
                        userInfoLock.unlock();
                    }
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
    @Transactional
    public void crawlingPlayList() {
        for (;;) {
            // 找到未爬取的歌单id
            PlayListPOExample example = new PlayListPOExample();
            example.createCriteria().andCrawlingStatusEqualTo(CrawlingStatusEnum.UN_CRAWLERED);
            example.setPageInfo(new PaginationInfo(1, CrawlerConstant.CRAWLING_PLAY_LIST_BATCH_SIZE)); // 分批爬取
            List<PlayListPO> playListPOList = playListPOMapper.selectByExample(example);
            LogConstant.BUS.info("fetch play list success, fetch count:{}.", playListPOList.size());
            if (CollectionUtils.isEmpty(playListPOList)) {
                break;
            }

            for (PlayListPO playListPO : playListPOList) {
                crawlerExecutor.execute(() -> doCrawlingPlayList(playListPO));
            }
        }
        LogConstant.BUS.info("crawling play list done.");
    }

    @Override
    public void crawlingOnePlayList(Long playListId) {
        PlayListPOExample example = new PlayListPOExample();
        example.createCriteria().andResourceIdEqualTo(playListId);
        List<PlayListPO> playListPOList = playListPOMapper.selectByExample(example);
        PlayListPO playListPO = playListPOList.get(0);
        doCrawlingPlayList(playListPO);
        LogConstant.BUS.info("crawlingOnePlayList play list done.");
    }

    /**
     * 爬取一个歌单
     * 
     * @param playListPO
     */
    private void doCrawlingPlayList(PlayListPO playListPO) {
        Long playListId = playListPO.getResourceId();
        if (playListId == null) {
            LogConstant.BUS.error("doCrawlingPlayList failed, playListId in playListPO is null.");
            return;
        }

        // 开始爬取
        playListPO.setCrawlingStatus(CrawlingStatusEnum.CRAWLING);
        PlayListPOExample example = new PlayListPOExample();
        example.createCriteria().andResourceIdEqualTo(playListPO.getResourceId());
        playListPOMapper.updateByExampleSelective(playListPO, example);

        // 获取歌单信息，获取歌单里面的歌曲，初始化歌曲表的id、名字、爬取状态，插入表单-歌曲关系
        try {
            Document doc = Jsoup.connect(CrawlerConstant.getPlayListPage(playListId)).get();
            if (doc == null) {
                LogConstant.BUS.error("doc not exists, playListId={}.", playListId);
                return;
            }
            Element statisticElement = doc.selectFirst("div#content-operation"); // 收藏数、转发数、播放数等统计信息
            if (statisticElement == null) {
                LogConstant.BUS.error("statisticElement not exists, playListId={}.", playListId);
                return;
            }

            // 收藏数
            Integer favoritesCount;
            Element favElement = statisticElement.selectFirst("a[data-res-action=fav]");
            if (favElement != null) {
                String favCountStr = favElement.attr("data-count");
                if (StringUtils.isNotBlank(favCountStr)) {
                    favoritesCount = Integer.valueOf(favCountStr);
                    playListPO.setFavoritesCount(favoritesCount);
                }
            }

            // 转发数
            Integer forwardCount;
            Element shareElement = statisticElement.selectFirst("a[data-res-action=share]");
            if (favElement != null) {
                String shareCountStr = shareElement.attr("data-count");
                if (StringUtils.isNotBlank(shareCountStr)) {
                    forwardCount = Integer.valueOf(shareCountStr);
                    playListPO.setForwardCount(forwardCount);
                }
            }

            // 评论数
            Integer commentCount;
            Element commentElement = doc.selectFirst("span#cnt_comment_count");
            if (commentElement != null) {
                String commentCountStr = commentElement.text();
                if (StringUtils.isNotBlank(commentCountStr)) {
                    commentCount = Integer.valueOf(commentCountStr);
                    playListPO.setCommentCount(commentCount);
                }
            }

            // 播放数
            Integer playCount;
            Element playCountElement = doc.selectFirst("strong#play-count");
            if (playCountElement != null) {
                String playCountStr = playCountElement.text();
                if (StringUtils.isNotBlank(playCountStr)) {
                    playCount = Integer.valueOf(playCountStr);
                    playListPO.setPlayCount(playCount);
                }
            }

            // 歌单创建时间
            Element createTimeElement = doc.selectFirst("span.time.s-fc4");
            if (createTimeElement != null) {
                String createTimeString = createTimeElement.text();
                if (StringUtils.isNotBlank(createTimeString)) {
                    Pattern p = Pattern.compile("(\\d{4}-\\d{2}-\\d{2}).*");
                    Matcher m = p.matcher(createTimeString);
                    if (m.find()) {
                        String createTimeStr = m.group(1);
                        if (StringUtils.isNotBlank(createTimeStr)) {
                            Timestamp createTime = DateUtil.formatToTimestamp(createTimeStr, DateUtil.H_24_FORMAT);
                            playListPO.setCreateTime(createTime);
                        }
                    }
                }
            }

            // 歌曲数
            Integer songCount;
            Element songCountElement = doc.selectFirst("span#playlist-track-count");
            if (songCountElement != null) {
                String songCountString = songCountElement.text();
                if (StringUtils.isNotBlank(songCountString)) {
                    songCount = Integer.valueOf(songCountString);
                    playListPO.setSongCount(songCount);
                }
            }

            // 获取所有歌曲
            List<Long> songIdList = new ArrayList<>();
            Map<Long, String> songUrlMap = new HashMap<>(); // key:歌曲id,value:url

            Pattern songUrlPattern = Pattern.compile("^(/song\\?id=)(\\d+)$");
            Element songListElement = doc.selectFirst("div#song-list-pre-cache");
            if (songListElement != null) {
                Elements songElements = songListElement.select("a[href]");
                if (songElements != null) {
                    // 获取所有歌曲id
                    for (Element element : songElements) {
                        if (element != null) {
                            String songUrl = element.attr("href");
                            if (StringUtils.isNotBlank(songUrl)) {
                                Matcher m = songUrlPattern.matcher(songUrl);
                                if (m.find()) {
                                    String songIdStr = m.group(2);
                                    if (StringUtils.isNotBlank(songIdStr)) {
                                        Long songId = Long.valueOf(songIdStr);
                                        songIdList.add(songId);
                                        songUrlMap.put(songId, songUrl);
                                    }
                                }
                            }
                        }
                    }

                    if (CollectionUtils.isNotEmpty(songIdList)) {
                        // 更新歌单-歌曲对应关系表
                        List<PlayListSongRelationPO> playListSongRelationPOList = new ArrayList<>();
                        for (Long songId : songIdList) {
                            PlayListSongRelationPO playListSongRelationPO = new PlayListSongRelationPO();
                            playListSongRelationPO.setPlayListId(playListId);
                            playListSongRelationPO.setSongId(songId);
                            playListSongRelationPO.setPlayListTitle(playListPO.getTitle());
                            playListSongRelationPOList.add(playListSongRelationPO);
                        }
                        playListSongRelationPOMapper.batchInsert(playListSongRelationPOList);

                        // 初始化歌曲表
                        for (Long songId : songIdList) {
                            SongPO songPO = new SongPO();
                            songPO.setResourceId(songId);
                            songPO.setUrl(songUrlMap.get(songId));
                            songPO.setCrawlingStatus(CrawlingStatusEnum.UN_CRAWLERED);
                            songLock.lock();
                            try {
                                SongPOExample songPOExample = new SongPOExample();
                                songPOExample.createCriteria().andResourceIdEqualTo(songId);
                                int duplicateCount = songPOMapperExt.countByExample(songPOExample);
                                if (duplicateCount == 0) {
                                    songPOMapperExt.insertSelective(songPO);
                                }
                            } catch (Exception e) {
                                LogConstant.BUS.error("insert into song tabal failed:", e);
                            } finally {
                                songLock.unlock();
                            }
                        }
                    }
                }
            }

            // 爬取完成
            playListPOMapper.updateByExampleSelective(playListPO, example);
            playListPO.setCrawlingStatus(CrawlingStatusEnum.CRAWLERED);
        } catch (Exception e) {
            // 爬取失败
            playListPO.setCrawlingStatus(CrawlingStatusEnum.CRAWLING_FAILED);
            playListPOMapper.updateByExampleSelective(playListPO, example);
        }
    }

    public void crawlingSongInfo() {
        int offset = 0;
        int limit = CrawlerConstant.CRAWLING_SONG_INFO_BATCH_SIZE;


    }
}
