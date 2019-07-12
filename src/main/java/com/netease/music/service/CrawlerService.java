package com.netease.music.service;

import java.util.List;

public interface CrawlerService {
    /**
     * 从头爬
     */
    void autoCrawling();

    /**
     * 获取一个分类下的所有歌单id
     * @param categoryName
     */
    List<Long> getPlayListIdOneCategory(String categoryName);


    /**
     * 获取单个页面上的所有歌单id
     *
     * @param url
     */
    List<Long> getPlayListIdOnePage(String url);
}
