package com.netease.music.entity.bo;

import java.io.Serializable;
import java.util.List;

/**
 * 评论详情
 */
public class CommentDetailBO implements Serializable {
    private static final long serialVersionUID = 1988086024587878518L;

    private UserInfoBO user;
    private List<BeReplied> beReplied;
    private PendantDataBO pendantData;
    private String showFloorComment;
    private Integer status;
    private Long commentId;
    private String content;
    private Long time;
    private Integer likedCount;
    private String expressionUrl;
    private Integer commentLocationType;
    private Long parentCommentId;
    private DecorationBO decoration;
    private Boolean repliedMark;
    private Boolean liked;

    public UserInfoBO getUser() {
        return user;
    }

    public void setUser(UserInfoBO user) {
        this.user = user;
    }

    public List<BeReplied> getBeReplied() {
        return beReplied;
    }

    public void setBeReplied(List<BeReplied> beReplied) {
        this.beReplied = beReplied;
    }

    public PendantDataBO getPendantData() {
        return pendantData;
    }

    public void setPendantData(PendantDataBO pendantData) {
        this.pendantData = pendantData;
    }

    public String getShowFloorComment() {
        return showFloorComment;
    }

    public void setShowFloorComment(String showFloorComment) {
        this.showFloorComment = showFloorComment;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Integer getLikedCount() {
        return likedCount;
    }

    public void setLikedCount(Integer likedCount) {
        this.likedCount = likedCount;
    }

    public String getExpressionUrl() {
        return expressionUrl;
    }

    public void setExpressionUrl(String expressionUrl) {
        this.expressionUrl = expressionUrl;
    }

    public Integer getCommentLocationType() {
        return commentLocationType;
    }

    public void setCommentLocationType(Integer commentLocationType) {
        this.commentLocationType = commentLocationType;
    }

    public Long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public DecorationBO getDecoration() {
        return decoration;
    }

    public void setDecoration(DecorationBO decoration) {
        this.decoration = decoration;
    }

    public Boolean getRepliedMark() {
        return repliedMark;
    }

    public void setRepliedMark(Boolean repliedMark) {
        this.repliedMark = repliedMark;
    }

    public Boolean getLiked() {
        return liked;
    }

    public void setLiked(Boolean liked) {
        this.liked = liked;
    }

    public static class BeReplied {
        private UserInfoBO user;
        private Long beRepliedCommentId;
        private String content;
        private Integer status;
        private String expressionUrl;

        public UserInfoBO getUser() {
            return user;
        }

        public void setUser(UserInfoBO user) {
            this.user = user;
        }

        public Long getBeRepliedCommentId() {
            return beRepliedCommentId;
        }

        public void setBeRepliedCommentId(Long beRepliedCommentId) {
            this.beRepliedCommentId = beRepliedCommentId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getExpressionUrl() {
            return expressionUrl;
        }

        public void setExpressionUrl(String expressionUrl) {
            this.expressionUrl = expressionUrl;
        }
    }

    public static class PendantDataBO {
        private Long id;
        private String imageUrl;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }

    public static class DecorationBO {

    }

}
