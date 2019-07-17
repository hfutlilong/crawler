package com.netease.music.service;

import com.netease.music.dao.po.SongPO;
import com.netease.music.entity.bo.ProxyBO;

public interface CrawlerService {
    /**
     * 自动爬取入口
     * 
     * @throws InterruptedException
     */
    void autoCrawling() throws InterruptedException;

    /**
     * 初始化，获取所有歌单
     */
    void initCrawling() throws InterruptedException;

    /**
     * 初始化一个类别下的歌单
     * @param categoryName
     */
    void initPlayListOneCategory(String categoryName);

    /**
     * 爬取歌单
     */
    void crawlingPlayList() throws InterruptedException;

    /**
     * 爬取一个歌单
     * @param playListId
     */
    void crawlingOnePlayList(Long playListId);

    /**
     * 爬取所有歌曲信息
     */
    void crawlingSongInfo() throws InterruptedException;

    /**
     * 爬取歌曲信息
     * @param songId
     */
    void doCrawlingSongInfo(Long songId);

    /**
     * 爬取歌单评论
     */
    void crawlingPlayListComment() throws InterruptedException;

    /**
     * 爬取指定歌单评论
     * @param playListId
     */
    void doCrawlingPlayListComment(Long playListId);

    /**
     * 爬取歌曲评论
     */
    void crawlingSongComment() throws InterruptedException;

    /**
     * 爬取一首歌的评论
     * @param songId
     */
    void doCrawlingSongComment(Long songId);

    /**
     * 获取一个可用的ip代理
     * @return
     */
    ProxyBO getAvailableProxy();
}
