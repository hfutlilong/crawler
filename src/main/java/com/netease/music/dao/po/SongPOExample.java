package com.netease.music.dao.po;

import com.netease.kaola.cs.utils.pagination.PaginationInfo;
import com.netease.music.entity.enums.CrawlingStatusEnum;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SongPOExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected PaginationInfo pageInfo;

    public SongPOExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setPageInfo(PaginationInfo pageInfo) {
        this.pageInfo=pageInfo;
    }

    public PaginationInfo getPageInfo() {
        return pageInfo;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> crawlingStatusCriteria;

        protected List<Criterion> allCriteria;

        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
            crawlingStatusCriteria = new ArrayList<Criterion>();
        }

        public List<Criterion> getCrawlingStatusCriteria() {
            return crawlingStatusCriteria;
        }

        protected void addCrawlingStatusCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            crawlingStatusCriteria.add(new Criterion(condition, value, "com.netease.music.dao.typehandler.CrawlingStatusEnumHandler"));
            allCriteria = null;
        }

        protected void addCrawlingStatusCriterion(String condition, CrawlingStatusEnum value1, CrawlingStatusEnum value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            crawlingStatusCriteria.add(new Criterion(condition, value1, value2, "com.netease.music.dao.typehandler.CrawlingStatusEnumHandler"));
            allCriteria = null;
        }

        public boolean isValid() {
            return criteria.size() > 0
                || crawlingStatusCriteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            if (allCriteria == null) {
                allCriteria = new ArrayList<Criterion>();
                allCriteria.addAll(criteria);
                allCriteria.addAll(crawlingStatusCriteria);
            }
            return allCriteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
            allCriteria = null;
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
            allCriteria = null;
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
            allCriteria = null;
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andResourceIdIsNull() {
            addCriterion("resource_id is null");
            return (Criteria) this;
        }

        public Criteria andResourceIdIsNotNull() {
            addCriterion("resource_id is not null");
            return (Criteria) this;
        }

        public Criteria andResourceIdEqualTo(Long value) {
            addCriterion("resource_id =", value, "resourceId");
            return (Criteria) this;
        }

        public Criteria andResourceIdNotEqualTo(Long value) {
            addCriterion("resource_id <>", value, "resourceId");
            return (Criteria) this;
        }

        public Criteria andResourceIdGreaterThan(Long value) {
            addCriterion("resource_id >", value, "resourceId");
            return (Criteria) this;
        }

        public Criteria andResourceIdGreaterThanOrEqualTo(Long value) {
            addCriterion("resource_id >=", value, "resourceId");
            return (Criteria) this;
        }

        public Criteria andResourceIdLessThan(Long value) {
            addCriterion("resource_id <", value, "resourceId");
            return (Criteria) this;
        }

        public Criteria andResourceIdLessThanOrEqualTo(Long value) {
            addCriterion("resource_id <=", value, "resourceId");
            return (Criteria) this;
        }

        public Criteria andResourceIdIn(List<Long> values) {
            addCriterion("resource_id in", values, "resourceId");
            return (Criteria) this;
        }

        public Criteria andResourceIdNotIn(List<Long> values) {
            addCriterion("resource_id not in", values, "resourceId");
            return (Criteria) this;
        }

        public Criteria andResourceIdBetween(Long value1, Long value2) {
            addCriterion("resource_id between", value1, value2, "resourceId");
            return (Criteria) this;
        }

        public Criteria andResourceIdNotBetween(Long value1, Long value2) {
            addCriterion("resource_id not between", value1, value2, "resourceId");
            return (Criteria) this;
        }

        public Criteria andUrlIsNull() {
            addCriterion("url is null");
            return (Criteria) this;
        }

        public Criteria andUrlIsNotNull() {
            addCriterion("url is not null");
            return (Criteria) this;
        }

        public Criteria andUrlEqualTo(String value) {
            addCriterion("url =", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotEqualTo(String value) {
            addCriterion("url <>", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThan(String value) {
            addCriterion("url >", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThanOrEqualTo(String value) {
            addCriterion("url >=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThan(String value) {
            addCriterion("url <", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThanOrEqualTo(String value) {
            addCriterion("url <=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLike(String value) {
            addCriterion("url like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotLike(String value) {
            addCriterion("url not like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlIn(List<String> values) {
            addCriterion("url in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotIn(List<String> values) {
            addCriterion("url not in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlBetween(String value1, String value2) {
            addCriterion("url between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotBetween(String value1, String value2) {
            addCriterion("url not between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andSubTitleIsNull() {
            addCriterion("sub_title is null");
            return (Criteria) this;
        }

        public Criteria andSubTitleIsNotNull() {
            addCriterion("sub_title is not null");
            return (Criteria) this;
        }

        public Criteria andSubTitleEqualTo(String value) {
            addCriterion("sub_title =", value, "subTitle");
            return (Criteria) this;
        }

        public Criteria andSubTitleNotEqualTo(String value) {
            addCriterion("sub_title <>", value, "subTitle");
            return (Criteria) this;
        }

        public Criteria andSubTitleGreaterThan(String value) {
            addCriterion("sub_title >", value, "subTitle");
            return (Criteria) this;
        }

        public Criteria andSubTitleGreaterThanOrEqualTo(String value) {
            addCriterion("sub_title >=", value, "subTitle");
            return (Criteria) this;
        }

        public Criteria andSubTitleLessThan(String value) {
            addCriterion("sub_title <", value, "subTitle");
            return (Criteria) this;
        }

        public Criteria andSubTitleLessThanOrEqualTo(String value) {
            addCriterion("sub_title <=", value, "subTitle");
            return (Criteria) this;
        }

        public Criteria andSubTitleLike(String value) {
            addCriterion("sub_title like", value, "subTitle");
            return (Criteria) this;
        }

        public Criteria andSubTitleNotLike(String value) {
            addCriterion("sub_title not like", value, "subTitle");
            return (Criteria) this;
        }

        public Criteria andSubTitleIn(List<String> values) {
            addCriterion("sub_title in", values, "subTitle");
            return (Criteria) this;
        }

        public Criteria andSubTitleNotIn(List<String> values) {
            addCriterion("sub_title not in", values, "subTitle");
            return (Criteria) this;
        }

        public Criteria andSubTitleBetween(String value1, String value2) {
            addCriterion("sub_title between", value1, value2, "subTitle");
            return (Criteria) this;
        }

        public Criteria andSubTitleNotBetween(String value1, String value2) {
            addCriterion("sub_title not between", value1, value2, "subTitle");
            return (Criteria) this;
        }

        public Criteria andArtistIdIsNull() {
            addCriterion("artist_id is null");
            return (Criteria) this;
        }

        public Criteria andArtistIdIsNotNull() {
            addCriterion("artist_id is not null");
            return (Criteria) this;
        }

        public Criteria andArtistIdEqualTo(String value) {
            addCriterion("artist_id =", value, "artistId");
            return (Criteria) this;
        }

        public Criteria andArtistIdNotEqualTo(String value) {
            addCriterion("artist_id <>", value, "artistId");
            return (Criteria) this;
        }

        public Criteria andArtistIdGreaterThan(String value) {
            addCriterion("artist_id >", value, "artistId");
            return (Criteria) this;
        }

        public Criteria andArtistIdGreaterThanOrEqualTo(String value) {
            addCriterion("artist_id >=", value, "artistId");
            return (Criteria) this;
        }

        public Criteria andArtistIdLessThan(String value) {
            addCriterion("artist_id <", value, "artistId");
            return (Criteria) this;
        }

        public Criteria andArtistIdLessThanOrEqualTo(String value) {
            addCriterion("artist_id <=", value, "artistId");
            return (Criteria) this;
        }

        public Criteria andArtistIdLike(String value) {
            addCriterion("artist_id like", value, "artistId");
            return (Criteria) this;
        }

        public Criteria andArtistIdNotLike(String value) {
            addCriterion("artist_id not like", value, "artistId");
            return (Criteria) this;
        }

        public Criteria andArtistIdIn(List<String> values) {
            addCriterion("artist_id in", values, "artistId");
            return (Criteria) this;
        }

        public Criteria andArtistIdNotIn(List<String> values) {
            addCriterion("artist_id not in", values, "artistId");
            return (Criteria) this;
        }

        public Criteria andArtistIdBetween(String value1, String value2) {
            addCriterion("artist_id between", value1, value2, "artistId");
            return (Criteria) this;
        }

        public Criteria andArtistIdNotBetween(String value1, String value2) {
            addCriterion("artist_id not between", value1, value2, "artistId");
            return (Criteria) this;
        }

        public Criteria andArtistNameIsNull() {
            addCriterion("artist_name is null");
            return (Criteria) this;
        }

        public Criteria andArtistNameIsNotNull() {
            addCriterion("artist_name is not null");
            return (Criteria) this;
        }

        public Criteria andArtistNameEqualTo(String value) {
            addCriterion("artist_name =", value, "artistName");
            return (Criteria) this;
        }

        public Criteria andArtistNameNotEqualTo(String value) {
            addCriterion("artist_name <>", value, "artistName");
            return (Criteria) this;
        }

        public Criteria andArtistNameGreaterThan(String value) {
            addCriterion("artist_name >", value, "artistName");
            return (Criteria) this;
        }

        public Criteria andArtistNameGreaterThanOrEqualTo(String value) {
            addCriterion("artist_name >=", value, "artistName");
            return (Criteria) this;
        }

        public Criteria andArtistNameLessThan(String value) {
            addCriterion("artist_name <", value, "artistName");
            return (Criteria) this;
        }

        public Criteria andArtistNameLessThanOrEqualTo(String value) {
            addCriterion("artist_name <=", value, "artistName");
            return (Criteria) this;
        }

        public Criteria andArtistNameLike(String value) {
            addCriterion("artist_name like", value, "artistName");
            return (Criteria) this;
        }

        public Criteria andArtistNameNotLike(String value) {
            addCriterion("artist_name not like", value, "artistName");
            return (Criteria) this;
        }

        public Criteria andArtistNameIn(List<String> values) {
            addCriterion("artist_name in", values, "artistName");
            return (Criteria) this;
        }

        public Criteria andArtistNameNotIn(List<String> values) {
            addCriterion("artist_name not in", values, "artistName");
            return (Criteria) this;
        }

        public Criteria andArtistNameBetween(String value1, String value2) {
            addCriterion("artist_name between", value1, value2, "artistName");
            return (Criteria) this;
        }

        public Criteria andArtistNameNotBetween(String value1, String value2) {
            addCriterion("artist_name not between", value1, value2, "artistName");
            return (Criteria) this;
        }

        public Criteria andAlbumIdIsNull() {
            addCriterion("album_id is null");
            return (Criteria) this;
        }

        public Criteria andAlbumIdIsNotNull() {
            addCriterion("album_id is not null");
            return (Criteria) this;
        }

        public Criteria andAlbumIdEqualTo(Long value) {
            addCriterion("album_id =", value, "albumId");
            return (Criteria) this;
        }

        public Criteria andAlbumIdNotEqualTo(Long value) {
            addCriterion("album_id <>", value, "albumId");
            return (Criteria) this;
        }

        public Criteria andAlbumIdGreaterThan(Long value) {
            addCriterion("album_id >", value, "albumId");
            return (Criteria) this;
        }

        public Criteria andAlbumIdGreaterThanOrEqualTo(Long value) {
            addCriterion("album_id >=", value, "albumId");
            return (Criteria) this;
        }

        public Criteria andAlbumIdLessThan(Long value) {
            addCriterion("album_id <", value, "albumId");
            return (Criteria) this;
        }

        public Criteria andAlbumIdLessThanOrEqualTo(Long value) {
            addCriterion("album_id <=", value, "albumId");
            return (Criteria) this;
        }

        public Criteria andAlbumIdIn(List<Long> values) {
            addCriterion("album_id in", values, "albumId");
            return (Criteria) this;
        }

        public Criteria andAlbumIdNotIn(List<Long> values) {
            addCriterion("album_id not in", values, "albumId");
            return (Criteria) this;
        }

        public Criteria andAlbumIdBetween(Long value1, Long value2) {
            addCriterion("album_id between", value1, value2, "albumId");
            return (Criteria) this;
        }

        public Criteria andAlbumIdNotBetween(Long value1, Long value2) {
            addCriterion("album_id not between", value1, value2, "albumId");
            return (Criteria) this;
        }

        public Criteria andAlbumNameIsNull() {
            addCriterion("album_name is null");
            return (Criteria) this;
        }

        public Criteria andAlbumNameIsNotNull() {
            addCriterion("album_name is not null");
            return (Criteria) this;
        }

        public Criteria andAlbumNameEqualTo(String value) {
            addCriterion("album_name =", value, "albumName");
            return (Criteria) this;
        }

        public Criteria andAlbumNameNotEqualTo(String value) {
            addCriterion("album_name <>", value, "albumName");
            return (Criteria) this;
        }

        public Criteria andAlbumNameGreaterThan(String value) {
            addCriterion("album_name >", value, "albumName");
            return (Criteria) this;
        }

        public Criteria andAlbumNameGreaterThanOrEqualTo(String value) {
            addCriterion("album_name >=", value, "albumName");
            return (Criteria) this;
        }

        public Criteria andAlbumNameLessThan(String value) {
            addCriterion("album_name <", value, "albumName");
            return (Criteria) this;
        }

        public Criteria andAlbumNameLessThanOrEqualTo(String value) {
            addCriterion("album_name <=", value, "albumName");
            return (Criteria) this;
        }

        public Criteria andAlbumNameLike(String value) {
            addCriterion("album_name like", value, "albumName");
            return (Criteria) this;
        }

        public Criteria andAlbumNameNotLike(String value) {
            addCriterion("album_name not like", value, "albumName");
            return (Criteria) this;
        }

        public Criteria andAlbumNameIn(List<String> values) {
            addCriterion("album_name in", values, "albumName");
            return (Criteria) this;
        }

        public Criteria andAlbumNameNotIn(List<String> values) {
            addCriterion("album_name not in", values, "albumName");
            return (Criteria) this;
        }

        public Criteria andAlbumNameBetween(String value1, String value2) {
            addCriterion("album_name between", value1, value2, "albumName");
            return (Criteria) this;
        }

        public Criteria andAlbumNameNotBetween(String value1, String value2) {
            addCriterion("album_name not between", value1, value2, "albumName");
            return (Criteria) this;
        }

        public Criteria andPlayDurationIsNull() {
            addCriterion("play_duration is null");
            return (Criteria) this;
        }

        public Criteria andPlayDurationIsNotNull() {
            addCriterion("play_duration is not null");
            return (Criteria) this;
        }

        public Criteria andPlayDurationEqualTo(String value) {
            addCriterion("play_duration =", value, "playDuration");
            return (Criteria) this;
        }

        public Criteria andPlayDurationNotEqualTo(String value) {
            addCriterion("play_duration <>", value, "playDuration");
            return (Criteria) this;
        }

        public Criteria andPlayDurationGreaterThan(String value) {
            addCriterion("play_duration >", value, "playDuration");
            return (Criteria) this;
        }

        public Criteria andPlayDurationGreaterThanOrEqualTo(String value) {
            addCriterion("play_duration >=", value, "playDuration");
            return (Criteria) this;
        }

        public Criteria andPlayDurationLessThan(String value) {
            addCriterion("play_duration <", value, "playDuration");
            return (Criteria) this;
        }

        public Criteria andPlayDurationLessThanOrEqualTo(String value) {
            addCriterion("play_duration <=", value, "playDuration");
            return (Criteria) this;
        }

        public Criteria andPlayDurationLike(String value) {
            addCriterion("play_duration like", value, "playDuration");
            return (Criteria) this;
        }

        public Criteria andPlayDurationNotLike(String value) {
            addCriterion("play_duration not like", value, "playDuration");
            return (Criteria) this;
        }

        public Criteria andPlayDurationIn(List<String> values) {
            addCriterion("play_duration in", values, "playDuration");
            return (Criteria) this;
        }

        public Criteria andPlayDurationNotIn(List<String> values) {
            addCriterion("play_duration not in", values, "playDuration");
            return (Criteria) this;
        }

        public Criteria andPlayDurationBetween(String value1, String value2) {
            addCriterion("play_duration between", value1, value2, "playDuration");
            return (Criteria) this;
        }

        public Criteria andPlayDurationNotBetween(String value1, String value2) {
            addCriterion("play_duration not between", value1, value2, "playDuration");
            return (Criteria) this;
        }

        public Criteria andCommentCountIsNull() {
            addCriterion("comment_count is null");
            return (Criteria) this;
        }

        public Criteria andCommentCountIsNotNull() {
            addCriterion("comment_count is not null");
            return (Criteria) this;
        }

        public Criteria andCommentCountEqualTo(Integer value) {
            addCriterion("comment_count =", value, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountNotEqualTo(Integer value) {
            addCriterion("comment_count <>", value, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountGreaterThan(Integer value) {
            addCriterion("comment_count >", value, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("comment_count >=", value, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountLessThan(Integer value) {
            addCriterion("comment_count <", value, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountLessThanOrEqualTo(Integer value) {
            addCriterion("comment_count <=", value, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountIn(List<Integer> values) {
            addCriterion("comment_count in", values, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountNotIn(List<Integer> values) {
            addCriterion("comment_count not in", values, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountBetween(Integer value1, Integer value2) {
            addCriterion("comment_count between", value1, value2, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCommentCountNotBetween(Integer value1, Integer value2) {
            addCriterion("comment_count not between", value1, value2, "commentCount");
            return (Criteria) this;
        }

        public Criteria andCrawlingStatusIsNull() {
            addCriterion("crawling_status is null");
            return (Criteria) this;
        }

        public Criteria andCrawlingStatusIsNotNull() {
            addCriterion("crawling_status is not null");
            return (Criteria) this;
        }

        public Criteria andCrawlingStatusEqualTo(CrawlingStatusEnum value) {
            addCrawlingStatusCriterion("crawling_status =", value, "crawlingStatus");
            return (Criteria) this;
        }

        public Criteria andCrawlingStatusNotEqualTo(CrawlingStatusEnum value) {
            addCrawlingStatusCriterion("crawling_status <>", value, "crawlingStatus");
            return (Criteria) this;
        }

        public Criteria andCrawlingStatusGreaterThan(CrawlingStatusEnum value) {
            addCrawlingStatusCriterion("crawling_status >", value, "crawlingStatus");
            return (Criteria) this;
        }

        public Criteria andCrawlingStatusGreaterThanOrEqualTo(CrawlingStatusEnum value) {
            addCrawlingStatusCriterion("crawling_status >=", value, "crawlingStatus");
            return (Criteria) this;
        }

        public Criteria andCrawlingStatusLessThan(CrawlingStatusEnum value) {
            addCrawlingStatusCriterion("crawling_status <", value, "crawlingStatus");
            return (Criteria) this;
        }

        public Criteria andCrawlingStatusLessThanOrEqualTo(CrawlingStatusEnum value) {
            addCrawlingStatusCriterion("crawling_status <=", value, "crawlingStatus");
            return (Criteria) this;
        }

        public Criteria andCrawlingStatusIn(List<CrawlingStatusEnum> values) {
            addCrawlingStatusCriterion("crawling_status in", values, "crawlingStatus");
            return (Criteria) this;
        }

        public Criteria andCrawlingStatusNotIn(List<CrawlingStatusEnum> values) {
            addCrawlingStatusCriterion("crawling_status not in", values, "crawlingStatus");
            return (Criteria) this;
        }

        public Criteria andCrawlingStatusBetween(CrawlingStatusEnum value1, CrawlingStatusEnum value2) {
            addCrawlingStatusCriterion("crawling_status between", value1, value2, "crawlingStatus");
            return (Criteria) this;
        }

        public Criteria andCrawlingStatusNotBetween(CrawlingStatusEnum value1, CrawlingStatusEnum value2) {
            addCrawlingStatusCriterion("crawling_status not between", value1, value2, "crawlingStatus");
            return (Criteria) this;
        }

        public Criteria andDbUpdateTimeIsNull() {
            addCriterion("db_update_time is null");
            return (Criteria) this;
        }

        public Criteria andDbUpdateTimeIsNotNull() {
            addCriterion("db_update_time is not null");
            return (Criteria) this;
        }

        public Criteria andDbUpdateTimeEqualTo(Date value) {
            addCriterion("db_update_time =", value, "dbUpdateTime");
            return (Criteria) this;
        }

        public Criteria andDbUpdateTimeNotEqualTo(Date value) {
            addCriterion("db_update_time <>", value, "dbUpdateTime");
            return (Criteria) this;
        }

        public Criteria andDbUpdateTimeGreaterThan(Date value) {
            addCriterion("db_update_time >", value, "dbUpdateTime");
            return (Criteria) this;
        }

        public Criteria andDbUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("db_update_time >=", value, "dbUpdateTime");
            return (Criteria) this;
        }

        public Criteria andDbUpdateTimeLessThan(Date value) {
            addCriterion("db_update_time <", value, "dbUpdateTime");
            return (Criteria) this;
        }

        public Criteria andDbUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("db_update_time <=", value, "dbUpdateTime");
            return (Criteria) this;
        }

        public Criteria andDbUpdateTimeIn(List<Date> values) {
            addCriterion("db_update_time in", values, "dbUpdateTime");
            return (Criteria) this;
        }

        public Criteria andDbUpdateTimeNotIn(List<Date> values) {
            addCriterion("db_update_time not in", values, "dbUpdateTime");
            return (Criteria) this;
        }

        public Criteria andDbUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("db_update_time between", value1, value2, "dbUpdateTime");
            return (Criteria) this;
        }

        public Criteria andDbUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("db_update_time not between", value1, value2, "dbUpdateTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}