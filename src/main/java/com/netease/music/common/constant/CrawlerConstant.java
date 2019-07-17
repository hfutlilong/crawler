package com.netease.music.common.constant;

public class CrawlerConstant {
    /**
     * 网易云音乐url前缀
     */
    public static final String NETEASE_MUSIC_PREFIX = "https://music.163.com/";

    /**
     * 爬取起始页面
     */
    public static final String START_URL = "https://music.163.com/discover/playlist/";

    /**
     * 每页展示多少个歌单
     */
    public static final int DEFAULT_PAGE_SIZE = 35;

    /**
     * 每页评论数
     */
    public static final int DEFAULT_COMMENT_PAGE_SIZE = 100;

    /**
     * 分批爬取歌单页，每批爬多少个歌单
     */
    public static final Integer CRAWLING_PLAY_LIST_BATCH_SIZE = 2000;

    /**
     * 分批爬取歌曲信息，每批爬取多少歌曲
     */
    public static final Integer CRAWLING_SONG_INFO_BATCH_SIZE = 2000;

    /**
     * 分批爬取评论信息
     */
    public static final Integer CRAWLING_COMMENT_BATCH_SIZE = 2000;

    /**
     * 歌单页url
     */
    private static final String PLAY_LISTS_URL = "https://music.163.com/discover/playlist/?order=hot&cat={category}&limit={limit}&offset={offset}";

    /**
     * 歌单列表
     * 
     * @param category
     * @param limit
     * @param offset
     * @return
     */
    public static String getPlayListsUrl(String category, int limit, int offset) {
        return PLAY_LISTS_URL.replace("{category}", category).replace("{limit}", String.valueOf(limit))
                .replace("{offset}", String.valueOf(offset));
    }

    /**
     * 歌单页面
     */
    private static final String PLAY_LIST_PAGE = "https://music.163.com/playlist?id={playListId}";

    /**
     * 歌单页面id
     * 
     * @param playListId
     * @return
     */
    public static String getPlayListPage(Long playListId) {
        return PLAY_LIST_PAGE.replace("{playListId}", String.valueOf(playListId));
    }

    /**
     * 歌曲页面
     */
    private static final String SONG_URL = "https://music.163.com/song?id={songId}";

    public static String getSongUrl(Long songId) {
        return SONG_URL.replace("{songId}", String.valueOf(songId));
    }

    /**
     * 歌单评论页面
     */
    private static final String PLAY_LIST_COMMENT = "https://music.163.com/api/v1/resource/comments/A_PL_0_{playListId}?limit={limit}&offset={offset}";

    public static String getPlayListComment(Long playListId, int limit, int offset) {
        return PLAY_LIST_COMMENT.replace("{playListId}", String.valueOf(playListId))
                .replace("{limit}", String.valueOf(limit)).replace("{offset}", String.valueOf(offset));
    }
}
