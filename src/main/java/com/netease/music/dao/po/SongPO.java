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