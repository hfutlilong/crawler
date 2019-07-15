package com.netease.music.service;

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
     * 爬取歌单
     */
    void crawlingPlayList();
}
