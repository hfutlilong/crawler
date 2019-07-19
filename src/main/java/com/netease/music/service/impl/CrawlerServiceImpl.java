package com.netease.music.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.netease.kaola.cs.utils.DateUtil;
import com.netease.kaola.cs.utils.fastjson.FastJsonUtil;
import com.netease.kaola.cs.utils.pagination.PaginationInfo;
import com.netease.music.dao.mapper.*;
import com.netease.music.dao.po.*;
import com.netease.music.entity.bo.*;
import com.netease.music.common.constant.CrawlerConstant;
import com.netease.music.common.log.LogConstant;
import com.netease.music.entity.enums.CrawlingStatusEnum;
import com.netease.music.entity.enums.PageTypeEnum;
import com.netease.music.service.CrawlerService;
import com.netease.music.service.ProxyIpService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CrawlerServiceImpl implements CrawlerService {
    @Autowired
    private ProxyIpService proxyIpService;

    @Autowired
    private PlaylistCategoryPOMapperExt playlistCategoryPOMapper;

    @Autowired
    private PlayListPOMapperExt playListPOMapper;

    @Autowired
    private PlayListSongRelationPOMapperExt playListSongRelationPOMapper;

    @Autowired
    private SongPOMapperExt songPOMapper;

    @Autowired
    private UserInfoPOMapperExt userInfoPOMapper;

    @Autowired
    private MusicCommentPOMapperExt musicCommentPOMapper;

    private Lock playListCategoryLock = new ReentrantLock();

    private Lock playListLock = new ReentrantLock();

    private Lock songLock = new ReentrantLock();

    private Lock userInfoLock = new ReentrantLock();

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
        // initCrawling();
        // // 发送初始化完成事件
        // CrawlerEventPublisher.publish(new InitPlayListFinishEvent());
        // 爬取歌单列表
        // crawlingPlayList();
        // LogConstant.BUS.info("歌单爬完啦！！！ crawlingPlayList Done!!!");

        // 爬取歌曲信息
        // crawlingSongInfo();
        // LogConstant.BUS.info("歌曲信息爬完啦！！！ crawlingSongInfo Done!!!");

        // 爬取歌单评论
//        crawlingPlayListComment();
        // 爬取歌曲评论
        crawlingSongComment();
    }

    /**
     * 抓取所有歌单，存入数据库
     * 
     * @throws InterruptedException
     */
    public void initCrawling() throws InterruptedException {
        // 获取全部歌单类别
        // List<String> categoryNameList = getAllCategoryNames();

        // Mock数据
        List<String> categoryNameList = Arrays.asList("全部");

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
                int duplicateCount = playlistCategoryPOMapper.countByExample(playlistCategoryPOExample);
                if (duplicateCount == 0) {
                    PlaylistCategoryPO playlistCategoryPO = new PlaylistCategoryPO();
                    playlistCategoryPO.setCategoryName(categoryName);
                    playlistCategoryPO.setCrawlingStatus(CrawlingStatusEnum.UN_CRAWLERED);
                    playlistCategoryPOMapper.insertSelective(playlistCategoryPO);
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
     *
     * @param categoryName
     * @param crawlingStatusEnum
     */
    private void updatePlayListCategoryCrawlingStatus(String categoryName, CrawlingStatusEnum crawlingStatusEnum) {
        PlaylistCategoryPO playlistCategoryPO = new PlaylistCategoryPO();
        playlistCategoryPO.setCategoryName(categoryName);
        playlistCategoryPO.setCrawlingStatus(crawlingStatusEnum);
        PlaylistCategoryPOExample playlistCategoryPOExample = new PlaylistCategoryPOExample();
        playlistCategoryPOExample.createCriteria().andCategoryNameEqualTo(categoryName);
        playlistCategoryPOMapper.updateByExampleSelective(playlistCategoryPO, playlistCategoryPOExample);
    }

    /**
     * 获取一个页面的所有歌单
     *
     * @param url
     * @return
     * @throws Exception
     */
    private List<PlayListDetailBO> getPlayListOnePage(String url) throws InterruptedException {
        Document doc = proxyIpService.getDocByHttpsProxy(url);
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
    private List<String> getAllCategoryNames() throws InterruptedException {
        List<String> categoryNameList = new ArrayList<>();

        String startUrl = CrawlerConstant.START_URL;
        Document doc = proxyIpService.getDocByHttpsProxy(startUrl);

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
    public void crawlingPlayList() throws InterruptedException {
        // 找到未爬取的歌单id
        PlayListPOExample example = new PlayListPOExample();
        example.createCriteria().andCrawlingStatusEqualTo(CrawlingStatusEnum.UN_CRAWLERED);
        example.setPageInfo(new PaginationInfo(1, CrawlerConstant.CRAWLING_PLAY_LIST_BATCH_SIZE)); // 分批爬取

        for (;;) {
            List<PlayListPO> playListPOList = playListPOMapper.selectByExample(example);
            LogConstant.BUS.info("fetch play list success, fetch count:{}.", playListPOList.size());
            if (CollectionUtils.isEmpty(playListPOList)) {
                break;
            }

            CountDownLatch countDownLatch = new CountDownLatch(playListPOList.size());

            for (PlayListPO playListPO : playListPOList) {
                crawlerExecutor.execute(() -> {
                    try {
                        doCrawlingPlayList(playListPO);
                    } catch (Exception e) {
                        LogConstant.BUS.error("execute doCrawlingPlayList failed, playListPO={}.",
                                JSON.toJSONString(playListPO));
                    } finally {
                        countDownLatch.countDown();
                    }
                });
            }
            countDownLatch.await();
            LogConstant.BUS.info("crawling a batch play list success.");
        }

        LogConstant.BUS.info("crawling play list done.");
    }

    @Override
    public void crawlingOnePlayList(Long playListId) {
        PlayListPOExample example = new PlayListPOExample();
        example.createCriteria().andResourceIdEqualTo(playListId);
        List<PlayListPO> playListPOList = playListPOMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(playListPOList)) {
            return;
        }
        PlayListPO playListPO = playListPOList.get(0);
        doCrawlingPlayList(playListPO);
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

        LogConstant.BUS.info("start crawling play list: {}.", playListId);

        // 开始爬取
        playListPO.setCrawlingStatus(CrawlingStatusEnum.CRAWLING);
        PlayListPOExample example = new PlayListPOExample();
        example.createCriteria().andResourceIdEqualTo(playListPO.getResourceId());
        playListPOMapper.updateByExampleSelective(playListPO, example);

        // 获取歌单信息，获取歌单里面的歌曲，初始化歌曲表的id、名字、爬取状态，插入表单-歌曲关系
        try {
            Document doc = proxyIpService.getDocByHttpsProxy(CrawlerConstant.getPlayListPage(playListId));
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
            Integer favoritesCount = 0;
            try {
                Element favElement = statisticElement.selectFirst("a[data-res-action=fav]");
                if (favElement != null) {
                    String favCountStr = favElement.attr("data-count");
                    if (StringUtils.isNotBlank(favCountStr)) {
                        favoritesCount = Integer.valueOf(favCountStr);
                    }
                }
                playListPO.setFavoritesCount(favoritesCount);
            } catch (Exception e) {
                LogConstant.BUS.error("get favoritesCount failed, playListId={}.", playListId);
            }

            // 转发数
            Integer forwardCount = 0;
            try {
                Element shareElement = statisticElement.selectFirst("a[data-res-action=share]");
                if (shareElement != null) {
                    String shareCountStr = shareElement.attr("data-count");
                    if (StringUtils.isNotBlank(shareCountStr)) {
                        forwardCount = Integer.valueOf(shareCountStr);
                    }
                }
                playListPO.setForwardCount(forwardCount);
            } catch (Exception e) {
                LogConstant.BUS.error("get forwardCount failed, playListId={}.", playListId);
            }

            // 评论数
            Integer commentCount = 0;
            try {
                Element commentElement = doc.selectFirst("span#cnt_comment_count");
                if (commentElement != null) {
                    String commentCountStr = commentElement.text();
                    if (StringUtils.isNotBlank(commentCountStr)) {
                        commentCount = Integer.valueOf(commentCountStr);
                    }
                }
                playListPO.setCommentCount(commentCount);
            } catch (Exception e) {
                LogConstant.BUS.error("get commentCount failed, playListId={}.", playListId);
            }

            // 播放数
            Integer playCount = 0;
            try {
                Element playCountElement = doc.selectFirst("strong#play-count.s-fc6");
                if (playCountElement != null) {
                    String playCountStr = playCountElement.text();
                    if (StringUtils.isNotBlank(playCountStr)) {
                        playCount = Integer.valueOf(playCountStr);
                    }
                }
                playListPO.setPlayCount(playCount);
            } catch (Exception e) {
                LogConstant.BUS.error("get playCount failed, playListId={}.", playListId);
            }

            // 歌单创建时间
            Element createTimeElement = doc.selectFirst("span.time.s-fc4");
            try {
                if (createTimeElement != null) {
                    String createTimeString = createTimeElement.text();
                    if (StringUtils.isNotBlank(createTimeString)) {
                        Pattern p = Pattern.compile("(\\d{4}-\\d{2}-\\d{2}).*");
                        Matcher m = p.matcher(createTimeString);
                        if (m.find()) {
                            String createTimeStr = m.group(1);
                            if (StringUtils.isNotBlank(createTimeStr)) {
                                Timestamp createTime = DateUtil.formatToTimestamp(createTimeStr,
                                        DateUtil.DEFAULT_DATE_FORMAT);
                                playListPO.setCreateTime(createTime);
                            }
                        }
                    }
                }
            } catch (Exception e) {
                LogConstant.BUS.error("get createTimeElement failed, playListId={}.", playListId);
            }

            // 歌曲数
            Integer songCount = 0;
            try {
                Element songCountElement = doc.selectFirst("span#playlist-track-count");
                if (songCountElement != null) {
                    String songCountString = songCountElement.text();
                    if (StringUtils.isNotBlank(songCountString)) {
                        songCount = Integer.valueOf(songCountString);
                    }
                }
                playListPO.setSongCount(songCount);
            } catch (Exception e) {
                LogConstant.BUS.error("get songCount failed, playListId={}.", playListId);
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
                                int duplicateCount = songPOMapper.countByExample(songPOExample);
                                if (duplicateCount == 0) {
                                    songPOMapper.insertSelective(songPO);
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
            playListPO.setCrawlingStatus(CrawlingStatusEnum.CRAWLERED);
            playListPOMapper.updateByExampleSelective(playListPO, example);

            LogConstant.BUS.info("end crawling play list: {}.", playListId);
        } catch (Exception e) {
            // 爬取失败
            playListPO.setCrawlingStatus(CrawlingStatusEnum.CRAWLING_FAILED);
            playListPOMapper.updateByExampleSelective(playListPO, example);
            LogConstant.BUS.error("failed crawling play list: {}.", playListId);
        }
    }

    @Override
    public void crawlingSongInfo() throws InterruptedException {
        SongPOExample songPOExample = new SongPOExample();
        songPOExample.createCriteria().andCrawlingStatusEqualTo(CrawlingStatusEnum.UN_CRAWLERED);
        songPOExample.setPageInfo(new PaginationInfo(1, CrawlerConstant.CRAWLING_SONG_INFO_BATCH_SIZE)); // 分批爬取

        for (;;) {
            List<SongPO> songPOList = songPOMapper.selectByExample(songPOExample);
            LogConstant.BUS.info("fetch song success, fetch count:{}.", songPOList.size());
            if (CollectionUtils.isEmpty(songPOList)) {
                break;
            }
            CountDownLatch countDownLatch = new CountDownLatch(songPOList.size());
            for (SongPO songPO : songPOList) {
                crawlerExecutor.execute(() -> {
                    try {
                        doCrawlingSongInfo(songPO);
                    } catch (Exception e) {
                        LogConstant.BUS.error("execute doCrawlingPlayList failed, songPO={}.",
                                JSON.toJSONString(songPO));
                        SongPO songPONew = new SongPO();
                        songPONew.setResourceId(songPO.getResourceId());
                        songPONew.setCrawlingStatus(CrawlingStatusEnum.CRAWLING_FAILED);
                        SongPOExample example = new SongPOExample();
                        example.createCriteria().andResourceIdEqualTo(songPO.getResourceId());
                        songPOMapper.updateByExampleSelective(songPO, example);
                    } finally {
                        countDownLatch.countDown();
                    }
                });
            }
            countDownLatch.await();
            LogConstant.BUS.info("crawling a batch song success.");
        }
    }

    @Override
    public void doCrawlingSongInfo(Long songId) throws InterruptedException {
        SongPOExample example = new SongPOExample();
        example.createCriteria().andResourceIdEqualTo(songId);
        List<SongPO> songPOList = songPOMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(songPOList)) {
            LogConstant.BUS.info("no song for id:{}.", songId);
            return;
        }
        SongPO songPO = songPOList.get(0);
        LogConstant.BUS.info("Query song info success: {}.", JSON.toJSONString(songPO));
        doCrawlingSongInfo(songPO);
    }

    private void doCrawlingSongInfo(SongPO songPO) throws InterruptedException {
        if (songPO == null) {
            LogConstant.BUS.error("doCrawlingSongInfo failed, songPO is null.");
            return;
        }

        Long songId = songPO.getResourceId();
        if (songId == null) {
            LogConstant.BUS.error("songId is null, songPO={}.", JSON.toJSONString(songPO));
            return;
        }

        SongPOExample example = new SongPOExample();
        example.createCriteria().andResourceIdEqualTo(songPO.getResourceId());
        songPO.setCrawlingStatus(CrawlingStatusEnum.CRAWLING);
        songPOMapper.updateByExampleSelective(songPO, example);

        String songUrl = CrawlerConstant.getSongUrl(songId);
        Document doc = proxyIpService.getDocByHttpsProxy(songUrl);

        if (doc == null) {
            LogConstant.BUS.error("doc of url {} is null.", songUrl);
            return;
        }

        Element titleAndSingerElement = doc.selectFirst("div.cnt");
        if (titleAndSingerElement != null) {
            // 歌曲名
            Element titleElement = titleAndSingerElement.selectFirst("em.f-ff2");
            if (titleElement != null) {
                songPO.setTitle(titleElement.text());
            }
            // 副标题
            Element subTitleElement = titleAndSingerElement.selectFirst("div.subtit.f-fs1.f-ff2");
            if (subTitleElement != null) {
                songPO.setSubTitle(subTitleElement.text());
            }

            // 歌手
            Elements artistAlbumElements = titleAndSingerElement.select("p.des.s-fc4");
            Element artistElement = artistAlbumElements.get(0);
            Element albumElement = artistAlbumElements.get(1);

            if (artistElement != null) {
                Pattern artistPattern = Pattern.compile("^(/artist\\?id=)(\\d+)$");
                Elements artists = artistElement.select("a[href]");
                List<Long> artistIdList = new ArrayList<>();
                List<String> artistNameList = new ArrayList<>();

                if (artists != null && artists.size() > 0) {
                    for (Element element : artists) {
                        String artistUrl = element.attr("href");
                        if (StringUtils.isNotBlank(artistUrl)) {
                            Matcher matcher = artistPattern.matcher(artistUrl);
                            if (matcher.find()) {
                                Long artistId = Long.valueOf(matcher.group(2));
                                String artistName = element.text();

                                artistIdList.add(artistId);
                                artistNameList.add(artistName);
                            }
                        }
                    }

                    if (CollectionUtils.isNotEmpty(artistIdList)) {
                        songPO.setArtistId(StringUtils.join(artistIdList, ","));
                    }
                    if (CollectionUtils.isNotEmpty(artistNameList)) {
                        songPO.setArtistName(StringUtils.join(artistNameList, ","));
                    }
                }
            }

            // 专辑
            if (albumElement != null) {
                Pattern albumPattern = Pattern.compile("^(/album\\?id=)(\\d+)$");
                Element albumUrlElement = albumElement.selectFirst("a[href]");
                String albumUrl = albumUrlElement.attr("href");
                if (StringUtils.isNotBlank(albumUrl)) {
                    Matcher matcher = albumPattern.matcher(albumUrl);
                    if (matcher.find()) {
                        Long albumId = Long.valueOf(matcher.group(2));
                        String albumName = albumUrlElement.text();

                        songPO.setAlbumId(albumId);
                        songPO.setAlbumName(albumName);
                    }
                }
            }

            // 评论数
        }

        songPO.setCrawlingStatus(CrawlingStatusEnum.CRAWLERED);
        songPOMapper.updateByExampleSelective(songPO, example);
    }

    @Override
    public void crawlingPlayListComment() throws InterruptedException {
        // 找到未爬取评论的歌单id
        PlayListPOExample example = new PlayListPOExample();
        example.createCriteria().andCommentCrawlingStatusEqualTo(CrawlingStatusEnum.UN_CRAWLERED);
        example.setPageInfo(new PaginationInfo(1, CrawlerConstant.CRAWLING_COMMENT_BATCH_SIZE)); // 分批爬取

        for (;;) {
            List<PlayListPO> playListPOList = playListPOMapper.selectByExample(example);
            LogConstant.BUS.info("fetch play list success, fetch count:{}.", playListPOList.size());
            if (CollectionUtils.isEmpty(playListPOList)) {
                break;
            }

            CountDownLatch countDownLatch = new CountDownLatch(playListPOList.size());

            for (PlayListPO playListPO : playListPOList) {
                crawlerExecutor.execute(() -> {
                    try {
                        doCrawlingPlayListComment(playListPO);
                    } catch (Exception e) {
                        LogConstant.BUS.error("execute crawlingPlayListComment failed, playListPO={}.",
                                JSON.toJSONString(playListPO));
                    } finally {
                        countDownLatch.countDown();
                    }
                });
            }
            countDownLatch.await();
            LogConstant.BUS.info("crawling a batch play list success.");
        }

        LogConstant.BUS.info("crawling play list comment done.");
    }

    @Override
    public void doCrawlingPlayListComment(Long playListId) {
        PlayListPO playListPO = new PlayListPO();

        PlayListPOExample example = new PlayListPOExample();
        example.createCriteria().andResourceIdEqualTo(playListId);
        List<PlayListPO> playListPOList = playListPOMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(playListPOList)) {
            LogConstant.BUS.info("playlist not exist, playListId:{}.", playListId);
            playListPO.setResourceId(playListId);
        } else {
            playListPO = playListPOList.get(0);
            LogConstant.BUS.info("Query play list success: {}.", JSON.toJSONString(playListPO));
        }

        doCrawlingPlayListComment(playListPO);
    }

    private void doCrawlingPlayListComment(PlayListPO playListPO) {
        Long playListId = playListPO.getResourceId();
        if (playListId == null) {
            LogConstant.BUS.error("doCrawlingPlayListComment failed, playListId is null.");
            return;
        }

        PlayListPOExample playListPOExample = new PlayListPOExample();
        playListPOExample.createCriteria().andResourceIdEqualTo(playListId);
        PlayListPO playListPONew = new PlayListPO();
        playListPONew.setResourceId(playListId);
        playListPONew.setCommentCrawlingStatus(CrawlingStatusEnum.CRAWLING);
        playListPOMapper.updateByExampleSelective(playListPONew, playListPOExample);

        try {
            crawlingPlayListComment(playListId, playListPO, playListPOExample);
            playListPONew.setCommentCrawlingStatus(CrawlingStatusEnum.CRAWLERED);
            playListPOMapper.updateByExampleSelective(playListPONew, playListPOExample);
        } catch (Exception e) {
            LogConstant.BUS.error("crawling play list {} comment failed:{}.", playListId, e.getMessage(), e);
            // 爬取失败
            playListPONew.setCommentCrawlingStatus(CrawlingStatusEnum.CRAWLING_FAILED);
            playListPOMapper.updateByExampleSelective(playListPONew, playListPOExample);
        }
    }

    private void crawlingPlayListComment(Long playListId, PlayListPO playListPO, PlayListPOExample playListPOExample)
            throws InterruptedException {
        int offset = 0;
        int limit = CrawlerConstant.DEFAULT_COMMENT_PAGE_SIZE;
        boolean firstQuery = true; // 是不是首次获取评论，首次的话要填充歌单表的评论数
        Integer totalCommentCount = null;

        boolean hasMore = true; // 有没有更多评论
        boolean hasMoreHot = true;

        while (hasMore || hasMoreHot) {
            String playListCommentUrl = CrawlerConstant.getPlayListCommentUrl(playListId, limit, offset); // 获取评论请求url
            CommentBO commentBO = queryCommentInfo(playListCommentUrl);
            if (commentBO == null) {
                if (offset >= 5000) {
                    break;
                }

                LogConstant.BUS.error("playListId={}, commentBO from parse result is null, will continue..",
                        playListId);
                Thread.sleep(3000);
                continue;
            }

            if (hasMore) {
                try {
                    hasMore = commentBO.getMore();
                    totalCommentCount = commentBO.getTotal();
                    if (firstQuery && totalCommentCount != null) { // 更新歌单的评论数
                        PlayListPO playListPOWithCommentCount = new PlayListPO();
                        playListPOWithCommentCount.setCommentCount(totalCommentCount);
                        playListPOMapper.updateByExampleSelective(playListPOWithCommentCount, playListPOExample);
                        firstQuery = false;
                    }

                    List<CommentDetailBO> commentList = commentBO.getComments();
                    if (CollectionUtils.isNotEmpty(commentList)) {
                        for (CommentDetailBO commentDetailBO : commentList) {
                            if (commentDetailBO != null) {
                                updateUserInfo(commentDetailBO.getUser()); // 更新用户信息表
                                insertPlayListComment(commentDetailBO, playListPO); // 插入评论表
                            }
                        }
                    }

                    LogConstant.BUS.info(
                            "crawling play list latest comment success,playListId={},offset={},limit={}, total={}.",
                            playListId, offset, limit, totalCommentCount);
                    offset += limit;
                    if (totalCommentCount == null || offset > totalCommentCount) {
                        break;
                    }
                } catch (Exception e) {
                    LogConstant.BUS.error("crawling play list latest comment failed,playListId={},offset={},limit={}.",
                            playListId, offset, limit);
                }
            }

            if (hasMoreHot) {
                try {
                    hasMoreHot = commentBO.getMoreHot();
                    List<CommentDetailBO> commentList = commentBO.getHotComments();
                    if (CollectionUtils.isNotEmpty(commentList)) {
                        for (CommentDetailBO commentDetailBO : commentList) {
                            if (commentDetailBO != null) {
                                updateUserInfo(commentDetailBO.getUser()); // 更新用户信息表
                                insertPlayListComment(commentDetailBO, playListPO); // 插入评论表
                            }
                        }
                    }

                    LogConstant.BUS.info(
                            "crawling play list hot comment success, playListId={},offset={},limit={},total={}.",
                            playListId, offset, limit, totalCommentCount);
                    offset += limit;
                } catch (Exception e) {
                    LogConstant.BUS.error(
                            "crawling play list hot comment failed, playListId={},offset={},limit={},total={}.",
                            playListId, offset, limit, totalCommentCount);
                }
            }
        }

        LogConstant.BUS.info("crawling play list {} hot comment success, total:{}.", playListId, totalCommentCount);
    }

    /**
     * 拉取歌单或歌曲的评论信息
     * 
     * @param url
     * @return
     */
    private CommentBO queryCommentInfo(String url) throws InterruptedException {
        String jsonResponse = proxyIpService.getJsonResponseByHttpProxy(url);
        if (StringUtils.isBlank(jsonResponse)) {
            LogConstant.BUS.error("queryCommentInfo is null for url {}.", url);
            return null;
        }

        return FastJsonUtil.parse(jsonResponse, CommentBO.class);
    }

    private void updateUserInfo(UserInfoBO userInfoBO) {
        if (userInfoBO == null || userInfoBO.getUserId() == null) {
            LogConstant.BUS.error("updateUserInfo failed, userInfoBO or userId is null, userInfoBO={}.",
                    FastJsonUtil.toJSONString(userInfoBO));
            return;
        }

        Long userId = userInfoBO.getUserId();
        UserInfoPO userInfoPO = new UserInfoPO();
        userInfoPO.setUserId(userId);
        userInfoPO.setUserName(userInfoBO.getNickname());
        userInfoPO.setAvatarurl(userInfoBO.getAvatarUrl());
        userInfoPOMapper.insertOnDuplicateUpdate(userInfoPO);
    }

    private void insertPlayListComment(CommentDetailBO commentDetailBO, PlayListPO playListPO) {
        if (commentDetailBO == null || commentDetailBO.getCommentId() == null) {
            LogConstant.BUS.error(
                    "insertPlayListComment failed, commentDetailBO or commentId is null, commentDetailBO={}.",
                    FastJsonUtil.toJSONString(commentDetailBO));
            return;
        }

        MusicCommentPO musicCommentPO = new MusicCommentPO();
        musicCommentPO.setCommentId(commentDetailBO.getCommentId());
        musicCommentPO.setResourceId(playListPO.getResourceId());
        musicCommentPO.setPageType(PageTypeEnum.PLAY_LIST);
        musicCommentPO.setTitle(playListPO.getTitle());
        musicCommentPO.setCommentTime(new Timestamp(commentDetailBO.getTime()));
        musicCommentPO.setUserId(commentDetailBO.getUser().getUserId());
        musicCommentPO.setLikeCount(commentDetailBO.getLikedCount());
        musicCommentPO.setCommentContent(commentDetailBO.getContent());

        musicCommentPOMapper.insertOnDuplicateUpdate(musicCommentPO);
    }

    @Override
    public void crawlingSongComment() throws InterruptedException {
        // 找到未爬取评论的歌曲id
        SongPOExample example = new SongPOExample();
        example.createCriteria().andCommentCrawlingStatusEqualTo(CrawlingStatusEnum.UN_CRAWLERED);
        example.setPageInfo(new PaginationInfo(1, CrawlerConstant.CRAWLING_COMMENT_BATCH_SIZE)); // 分批爬取

        for (;;) {
            List<SongPO> songPOList = songPOMapper.selectByExample(example);
            LogConstant.BUS.info("fetch song  success, fetch count:{}.", songPOList.size());
            if (CollectionUtils.isEmpty(songPOList)) {
                break;
            }

            CountDownLatch countDownLatch = new CountDownLatch(songPOList.size());

            for (SongPO songPO : songPOList) {
                crawlerExecutor.execute(() -> {
                    try {
                        doCrawlingSongComment(songPO);
                    } catch (Exception e) {
                        LogConstant.BUS.error("execute crawlingSongComment failed, songPO={}.",
                                JSON.toJSONString(songPO));
                    } finally {
                        countDownLatch.countDown();
                    }
                });
            }
            countDownLatch.await();
            LogConstant.BUS.info("crawling a batch song success.");
        }

        LogConstant.BUS.info("crawling song comments done.");
    }

    @Override
    public void doCrawlingSongComment(Long songId) {
        SongPO songPO = new SongPO();

        SongPOExample songPOExample = new SongPOExample();
        songPOExample.createCriteria().andResourceIdEqualTo(songId);
        List<SongPO> songPOList = songPOMapper.selectByExample(songPOExample);
        if (CollectionUtils.isEmpty(songPOList)) {
            LogConstant.BUS.info("song not exist, songId:{}.", songId);
            songPO.setResourceId(songId);
        } else {
            songPO = songPOList.get(0);
            LogConstant.BUS.info("Query song success: {}.", JSON.toJSONString(songPO));
        }

        doCrawlingSongComment(songPO);
    }

    private void doCrawlingSongComment(SongPO songPO) {
        Long songId = songPO.getResourceId();
        if (songId == null) {
            LogConstant.BUS.error("doCrawlingSongComment failed, songId is null.");
            return;
        }

        SongPOExample songPOExample = new SongPOExample();
        songPOExample.createCriteria().andResourceIdEqualTo(songId);
        SongPO songPONew = new SongPO();
        songPONew.setResourceId(songId);
        songPONew.setCommentCrawlingStatus(CrawlingStatusEnum.CRAWLING);
        songPOMapper.updateByExampleSelective(songPONew, songPOExample);

        try {
            crawlingSongComment(songId, songPO, songPOExample);

            // 爬取成功
            songPONew.setCommentCrawlingStatus(CrawlingStatusEnum.CRAWLERED);
            songPOMapper.updateByExampleSelective(songPONew, songPOExample);
        } catch (Exception e) {
            LogConstant.BUS.error("crawling song {} comment failed:{}.", songId, e.getMessage(), e);
            // 爬取失败
            songPONew.setCommentCrawlingStatus(CrawlingStatusEnum.CRAWLING_FAILED);
            songPOMapper.updateByExampleSelective(songPONew, songPOExample);
        }

    }

    private void crawlingSongComment(Long songId, SongPO songPO, SongPOExample songPOExample)
            throws InterruptedException {
        int offset = 0;
        int limit = CrawlerConstant.DEFAULT_COMMENT_PAGE_SIZE;

        boolean firstQuery = true; // 是不是首次获取评论，首次的话要填充歌单表的评论数
        Integer totalCommentCount = null;

        boolean hasMore = true; // 有没有更多评论
        boolean hasMoreHot = true;

        // try {
        while (hasMore || hasMoreHot) {
            String songCommentUrl = CrawlerConstant.getSongCommentUrl(songId, limit, offset); // 获取评论请求url
            CommentBO commentBO = queryCommentInfo(songCommentUrl);
            if (commentBO == null) {
                if (offset >= 5000) {
                    break;
                }
                LogConstant.BUS.error("songId={}, commentBO from parse result is null, will continue.", songId);
                Thread.sleep(3000);
                continue;
            }

            if (hasMore) {
                try {
                    hasMore = commentBO.getMore();
                    totalCommentCount = commentBO.getTotal();
                    if (firstQuery && totalCommentCount != null) { // 更新歌单的评论数
                        SongPO songPOWithCommentCount = new SongPO();
                        songPOWithCommentCount.setCommentCount(totalCommentCount);
                        songPOMapper.updateByExampleSelective(songPOWithCommentCount, songPOExample);
                        firstQuery = false;
                    }

                    List<CommentDetailBO> commentList = commentBO.getComments();
                    if (CollectionUtils.isNotEmpty(commentList)) {
                        for (CommentDetailBO commentDetailBO : commentList) {
                            if (commentDetailBO != null) {
                                updateUserInfo(commentDetailBO.getUser()); // 更新用户信息表
                                insertSongComment(commentDetailBO, songPO); // 插入评论表
                            }
                        }
                    }

                    LogConstant.BUS.info(
                            "crawling latest song comment success, songId={}, offset={},limit={}, total={}.", songId,
                            offset, limit, totalCommentCount);
                    offset += limit;
                    if (totalCommentCount == null || offset > totalCommentCount) {
                        break;
                    }

                } catch (Exception e) {
                    LogConstant.BUS.error(
                            "doCrawlingSongComment latest failed,songId={}, offset={},limit={},totalCommentCount={}.",
                            songId, offset, limit, totalCommentCount);
                }
            }

            if (hasMoreHot) {
                try {
                    hasMoreHot = commentBO.getMoreHot();
                    List<CommentDetailBO> commentList = commentBO.getHotComments();
                    if (CollectionUtils.isNotEmpty(commentList)) {
                        for (CommentDetailBO commentDetailBO : commentList) {
                            if (commentDetailBO != null) {
                                updateUserInfo(commentDetailBO.getUser()); // 更新用户信息表
                                insertSongComment(commentDetailBO, songPO); // 插入评论表
                            }
                        }
                    }

                    LogConstant.BUS.info("crawling hot song comment success, songId={},offset={},limit={}.", songId,
                            offset, limit);
                    offset += limit;

                } catch (Exception e) {
                    LogConstant.BUS.error("doCrawlingSongComment hot failed, songId={},offset={},limit={}", songId,
                            offset, limit);
                }
            }
        }

        LogConstant.BUS.info("crawling song {} latest comment success, total num: {}.", songId, totalCommentCount);
    }

    private void insertSongComment(CommentDetailBO commentDetailBO, SongPO songPO) {
        if (commentDetailBO == null || commentDetailBO.getCommentId() == null) {
            LogConstant.BUS.error("insertSongComment failed, commentDetailBO or commentId is null, commentDetailBO={}.",
                    FastJsonUtil.toJSONString(commentDetailBO));
            return;
        }

        MusicCommentPO musicCommentPO = new MusicCommentPO();
        musicCommentPO.setCommentId(commentDetailBO.getCommentId());
        musicCommentPO.setResourceId(songPO.getResourceId());
        musicCommentPO.setPageType(PageTypeEnum.SONG);
        musicCommentPO.setTitle(songPO.getTitle());
        musicCommentPO.setCommentTime(new Timestamp(commentDetailBO.getTime()));
        musicCommentPO.setUserId(commentDetailBO.getUser().getUserId());
        musicCommentPO.setLikeCount(commentDetailBO.getLikedCount());
        musicCommentPO.setCommentContent(commentDetailBO.getContent());

        musicCommentPOMapper.insertOnDuplicateUpdate(musicCommentPO);
    }

}
