package com.netease.music.entity.bo;

import com.netease.music.entity.enums.CrawlingStatusEnum;
import com.netease.music.entity.enums.PageTypeEnum;

/**
 * 歌单详情
 */
public class PlayListDetailBO {
    /**
     * 资源id
     */
    private Long resourceId;

    /**
     * 页面类型：歌单
     */
    private PageTypeEnum pageTypeEnum = PageTypeEnum.PLAY_LIST;

    /**
     * url
     */
    private String url;

    /**
     * 标题（歌单名）
     */
    private String title;

    /**
     * 爬取状态，初始为未爬取
     */
    private CrawlingStatusEnum crawlingStatusEnum = CrawlingStatusEnum.UN_CRAWLERED;

    /**
     * 播放数
     */
    private Integer playCount;

    /**
     * 收藏数
     */
    private Integer favoritesCount;

    /**
     * 转发数
     */
    private Integer forwardCount;

    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * 歌曲数
     */
    private Integer songCount;

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public PageTypeEnum getPageTypeEnum() {
        return pageTypeEnum;
    }

    public void setPageTypeEnum(PageTypeEnum pageTypeEnum) {
        this.pageTypeEnum = pageTypeEnum;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CrawlingStatusEnum getCrawlingStatusEnum() {
        return crawlingStatusEnum;
    }

    public void setCrawlingStatusEnum(CrawlingStatusEnum crawlingStatusEnum) {
        this.crawlingStatusEnum = crawlingStatusEnum;
    }

    public Integer getPlayCount() {
        return playCount;
    }

    public void setPlayCount(Integer playCount) {
        this.playCount = playCount;
    }

    public Integer getFavoritesCount() {
        return favoritesCount;
    }

    public void setFavoritesCount(Integer favoritesCount) {
        this.favoritesCount = favoritesCount;
    }

    public Integer getForwardCount() {
        return forwardCount;
    }

    public void setForwardCount(Integer forwardCount) {
        this.forwardCount = forwardCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getSongCount() {
        return songCount;
    }

    public void setSongCount(Integer songCount) {
        this.songCount = songCount;
    }
}
