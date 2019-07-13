package com.netease.music.service;

import com.netease.music.entity.bo.PlayListDetailBO;

import java.util.List;

public interface CrawlerService {
    /**
     * 初始化，获取所有歌单信息
     */
    void initCrawling() throws InterruptedException;

    /**
     * 初始化一个类别的歌单
     * 
     * @param categoryName
     */
    void initPlayListOneCategory(String categoryName);

    /**
     * 获取一个页面的所有歌单
     * 
     * @param url
     * @return
     * @throws Exception
     */
    List<PlayListDetailBO> getPlayListOnePage(String url) throws Exception;

    /**
     * 获取歌单详情
     * 
     * @param url
     * @return
     */
    PlayListDetailBO getPlayListDetail(String url);
}
