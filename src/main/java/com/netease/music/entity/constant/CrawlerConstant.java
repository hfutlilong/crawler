package com.netease.music.entity.constant;

public class CrawlerConstant {
    /**
     * 爬取起始页面
     */
    public static final String START_URL = "https://music.163.com/discover/playlist/";

    /**
     * 每页展示多少个歌单
     */
    public static final int DEFAULT_PAGE_SIZE = 35;

    /**
     * 歌单页url
     */
    public static final String PLAY_LISTS_URL = "https://music.163.com/discover/playlist/?order=hot&cat={category}&limit={limit}&offset={offset}";

    /**
     * 获取歌单页url
     * @param category
     * @param limit
     * @param offset
     * @return
     */
    public static String getPlayListsUrl(String category, int limit, int offset) {
        return PLAY_LISTS_URL.replace("{category}", category).replace("{limit}", String.valueOf(limit))
                .replace("{offset}", String.valueOf(offset));
    }
}
