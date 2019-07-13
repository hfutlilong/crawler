package com.netease.music.dao.po;

import com.netease.music.entity.enums.PageTypeEnum;
import java.io.Serializable;
import java.util.Date;

public class MusicCommentPO implements Serializable {
    private Long id;

    private Long resourceId;

    private PageTypeEnum pageType;

    private String url;

    private String title;

    private Date commentTime;

    private Long userId;

    private Integer likeCount;

    private Date dbUpdateTime;

    private String content;

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

    public PageTypeEnum getPageType() {
        return pageType;
    }

    public void setPageType(PageTypeEnum pageType) {
        this.pageType = pageType;
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

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Date getDbUpdateTime() {
        return dbUpdateTime;
    }

    public void setDbUpdateTime(Date dbUpdateTime) {
        this.dbUpdateTime = dbUpdateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}