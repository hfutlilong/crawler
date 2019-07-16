package com.netease.music.dao.po;

import com.netease.music.entity.enums.CrawlingStatusEnum;
import java.io.Serializable;
import java.util.Date;

public class SongPO implements Serializable {
    private Long id;

    private Long resourceId;

    private String url;

    private String title;

    private Long artistId;

    private String artistName;

    private Long albumId;

    private String albumName;

    private String playDuration;

    private Integer commentCount;

    private CrawlingStatusEnum crawlingStatus;

    private Date dbUpdateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName == null ? null : artistName.trim();
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName == null ? null : albumName.trim();
    }

    public String getPlayDuration() {
        return playDuration;
    }

    public void setPlayDuration(String playDuration) {
        this.playDuration = playDuration == null ? null : playDuration.trim();
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public CrawlingStatusEnum getCrawlingStatus() {
        return crawlingStatus;
    }

    public void setCrawlingStatus(CrawlingStatusEnum crawlingStatus) {
        this.crawlingStatus = crawlingStatus;
    }

    public Date getDbUpdateTime() {
        return dbUpdateTime;
    }

    public void setDbUpdateTime(Date dbUpdateTime) {
        this.dbUpdateTime = dbUpdateTime;
    }
}