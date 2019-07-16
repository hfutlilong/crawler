package com.netease.music.service;

import com.netease.music.dao.po.SongPO;

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
     * @param songPO
     */
    void doCrawlingSongInfo(SongPO songPO);
}
