package com.netease.music.entity.bo;

import java.io.Serializable;
import java.util.List;

/**
 * 一条评论的完整信息
 */
public class CommentBO implements Serializable {
    private static final long serialVersionUID = 5931415701830855178L;

    private Boolean isMusician;
    private Long userId;
    private List<CommentDetailBO> topComments;
    private Boolean moreHot;
    private List<CommentDetailBO> hotComments;
    private Integer code;
    private List<CommentDetailBO> comments;
    private Integer total;
    private Boolean more;

    public Boolean getMusician() {
        return isMusician;
    }

    public void setMusician(Boolean musician) {
        isMusician = musician;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<CommentDetailBO> getTopComments() {
        return topComments;
    }

    public void setTopComments(List<CommentDetailBO> topComments) {
        this.topComments = topComments;
    }

    public Boolean getMoreHot() {
        return moreHot;
    }

    public void setMoreHot(Boolean moreHot) {
        this.moreHot = moreHot;
    }

    public List<CommentDetailBO> getHotComments() {
        return hotComments;
    }

    public void setHotComments(List<CommentDetailBO> hotComments) {
        this.hotComments = hotComments;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<CommentDetailBO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDetailBO> comments) {
        this.comments = comments;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Boolean getMore() {
        return more;
    }

    public void setMore(Boolean more) {
        this.more = more;
    }
}
