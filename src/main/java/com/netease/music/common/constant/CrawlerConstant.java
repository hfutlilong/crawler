package com.netease.music.common.constant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private static final String PLAY_LIST_COMMENT_URL = "https://music.163.com/api/v1/resource/comments/A_PL_0_{playListId}?limit={limit}&offset={offset}";

    public static String getPlayListCommentUrl(Long playListId, int limit, int offset) {
        return PLAY_LIST_COMMENT_URL.replace("{playListId}", String.valueOf(playListId))
                .replace("{limit}", String.valueOf(limit)).replace("{offset}", String.valueOf(offset));
    }

    /**
     * 歌单评论页面
     */
    private static final String SONG_COMMENT_URL = "http://music.163.com/api/v1/resource/comments/R_SO_4_{songId}?limit={limit}&offset={offset}";

    public static String getSongCommentUrl(Long songId, int limit, int offset) {
        return SONG_COMMENT_URL.replace("{songId}", String.valueOf(songId))
                .replace("{limit}", String.valueOf(limit)).replace("{offset}", String.valueOf(offset));
    }

    /**
     * 代理ip网站
     */
    private static String PROXY_WEBSITE_ADDR = "http://www.xiladaili.com/gaoni/{pageNo}/";

    public static String getProxyWebsiteAddr(int pageNo) {
        return PROXY_WEBSITE_ADDR.replace("{pageNo}", String.valueOf(pageNo));
    }

    public static final List<String> USER_AGENT_LIST = Arrays.asList("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; AcooBrowser; .NET CLR 1.1.4322; .NET CLR 2.0.50727)",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0; Acoo Browser; SLCC1; .NET CLR 2.0.50727; Media Center PC 5.0; .NET CLR 3.0.04506)",
            "Mozilla/4.0 (compatible; MSIE 7.0; AOL 9.5; AOLBuild 4337.35; Windows NT 5.1; .NET CLR 1.1.4322; .NET CLR 2.0.50727)",
            "Mozilla/5.0 (Windows; U; MSIE 9.0; Windows NT 9.0; en-US)",
            "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Win64; x64; Trident/5.0; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET CLR 2.0.50727; Media Center PC 6.0)",
            "Mozilla/5.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0; WOW64; Trident/4.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; .NET CLR 1.0.3705; .NET CLR 1.1.4322)",
            "Mozilla/4.0 (compatible; MSIE 7.0b; Windows NT 5.2; .NET CLR 1.1.4322; .NET CLR 2.0.50727; InfoPath.2; .NET CLR 3.0.04506.30)",
            "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN) AppleWebKit/523.15 (KHTML, like Gecko, Safari/419.3) Arora/0.3 (Change: 287 c9dfb30)",
            "Mozilla/5.0 (X11; U; Linux; en-US) AppleWebKit/527+ (KHTML, like Gecko, Safari/419.3) Arora/0.6",
            "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.8.1.2pre) Gecko/20070215 K-Ninja/2.1.1",
            "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9) Gecko/20080705 Firefox/3.0 Kapiko/3.0",
            "Mozilla/5.0 (X11; Linux i686; U;) Gecko/20070322 Kazehakase/0.4.5",
            "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.8) Gecko Fedora/1.9.0.8-1.fc10 Kazehakase/0.5.6",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_3) AppleWebKit/535.20 (KHTML, like Gecko) Chrome/19.0.1036.7 Safari/535.20",
            "Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; fr) Presto/2.9.168 Version/11.52",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.11 (KHTML, like Gecko) Chrome/20.0.1132.11 TaoBrowser/2.0 Safari/536.11",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.71 Safari/537.1 LBBROWSER",
            "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E; LBBROWSER)",
            "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 732; .NET4.0C; .NET4.0E; LBBROWSER)",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.84 Safari/535.11 LBBROWSER",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E)",
            "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E; QQBrowser/7.0.3698.400)",
            "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 732; .NET4.0C; .NET4.0E)",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; SV1; QQDownload 732; .NET4.0C; .NET4.0E; 360SE)",
            "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 732; .NET4.0C; .NET4.0E)",
            "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E)",
            "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.89 Safari/537.1",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.89 Safari/537.1",
            "Mozilla/5.0 (iPad; U; CPU OS 4_2_1 like Mac OS X; zh-cn) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8C148 Safari/6533.18.5",
            "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:2.0b13pre) Gecko/20110307 Firefox/4.0b13pre",
            "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:16.0) Gecko/20100101 Firefox/16.0",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11",
            "Mozilla/5.0 (X11; U; Linux x86_64; zh-CN; rv:1.9.2.10) Gecko/20100922 Ubuntu/10.10 (maverick) Firefox/3.6.10",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
}
