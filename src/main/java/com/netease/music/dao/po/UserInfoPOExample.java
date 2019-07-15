package com.netease.music.dao.po;

import com.netease.kaola.cs.utils.pagination.PaginationInfo;
import com.netease.music.entity.enums.SexEnum;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserInfoPOExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected PaginationInfo pageInfo;

    public UserInfoPOExample() {
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
        protected List<Criterion> sexCriteria;

        protected List<Criterion> allCriteria;

        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
            sexCriteria = new ArrayList<Criterion>();
        }

        public List<Criterion> getSexCriteria() {
            return sexCriteria;
        }

        protected void addSexCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            sexCriteria.add(new Criterion(condition, value, "com.netease.music.dao.typehandler.SexEnumHandler"));
            allCriteria = null;
        }

        protected void addSexCriterion(String condition, SexEnum value1, SexEnum value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            sexCriteria.add(new Criterion(condition, value1, value2, "com.netease.music.dao.typehandler.SexEnumHandler"));
            allCriteria = null;
        }

        public boolean isValid() {
            return criteria.size() > 0
                || sexCriteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            if (allCriteria == null) {
                allCriteria = new ArrayList<Criterion>();
                allCriteria.addAll(criteria);
                allCriteria.addAll(sexCriteria);
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

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Long value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Long value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Long value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Long value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Long value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Long> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Long> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Long value1, Long value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Long value1, Long value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNull() {
            addCriterion("user_name is null");
            return (Criteria) this;
        }

        public Criteria andUserNameIsNotNull() {
            addCriterion("user_name is not null");
            return (Criteria) this;
        }

        public Criteria andUserNameEqualTo(String value) {
            addCriterion("user_name =", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotEqualTo(String value) {
            addCriterion("user_name <>", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThan(String value) {
            addCriterion("user_name >", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameGreaterThanOrEqualTo(String value) {
            addCriterion("user_name >=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThan(String value) {
            addCriterion("user_name <", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLessThanOrEqualTo(String value) {
            addCriterion("user_name <=", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameLike(String value) {
            addCriterion("user_name like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotLike(String value) {
            addCriterion("user_name not like", value, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameIn(List<String> values) {
            addCriterion("user_name in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotIn(List<String> values) {
            addCriterion("user_name not in", values, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameBetween(String value1, String value2) {
            addCriterion("user_name between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andUserNameNotBetween(String value1, String value2) {
            addCriterion("user_name not between", value1, value2, "userName");
            return (Criteria) this;
        }

        public Criteria andSexIsNull() {
            addCriterion("sex is null");
            return (Criteria) this;
        }

        public Criteria andSexIsNotNull() {
            addCriterion("sex is not null");
            return (Criteria) this;
        }

        public Criteria andSexEqualTo(SexEnum value) {
            addSexCriterion("sex =", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotEqualTo(SexEnum value) {
            addSexCriterion("sex <>", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThan(SexEnum value) {
            addSexCriterion("sex >", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexGreaterThanOrEqualTo(SexEnum value) {
            addSexCriterion("sex >=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThan(SexEnum value) {
            addSexCriterion("sex <", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexLessThanOrEqualTo(SexEnum value) {
            addSexCriterion("sex <=", value, "sex");
            return (Criteria) this;
        }

        public Criteria andSexIn(List<SexEnum> values) {
            addSexCriterion("sex in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotIn(List<SexEnum> values) {
            addSexCriterion("sex not in", values, "sex");
            return (Criteria) this;
        }

        public Criteria andSexBetween(SexEnum value1, SexEnum value2) {
            addSexCriterion("sex between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andSexNotBetween(SexEnum value1, SexEnum value2) {
            addSexCriterion("sex not between", value1, value2, "sex");
            return (Criteria) this;
        }

        public Criteria andAgeIsNull() {
            addCriterion("age is null");
            return (Criteria) this;
        }

        public Criteria andAgeIsNotNull() {
            addCriterion("age is not null");
            return (Criteria) this;
        }

        public Criteria andAgeEqualTo(Byte value) {
            addCriterion("age =", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeNotEqualTo(Byte value) {
            addCriterion("age <>", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeGreaterThan(Byte value) {
            addCriterion("age >", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeGreaterThanOrEqualTo(Byte value) {
            addCriterion("age >=", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeLessThan(Byte value) {
            addCriterion("age <", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeLessThanOrEqualTo(Byte value) {
            addCriterion("age <=", value, "age");
            return (Criteria) this;
        }

        public Criteria andAgeIn(List<Byte> values) {
            addCriterion("age in", values, "age");
            return (Criteria) this;
        }

        public Criteria andAgeNotIn(List<Byte> values) {
            addCriterion("age not in", values, "age");
            return (Criteria) this;
        }

        public Criteria andAgeBetween(Byte value1, Byte value2) {
            addCriterion("age between", value1, value2, "age");
            return (Criteria) this;
        }

        public Criteria andAgeNotBetween(Byte value1, Byte value2) {
            addCriterion("age not between", value1, value2, "age");
            return (Criteria) this;
        }

        public Criteria andAgeDescriptionIsNull() {
            addCriterion("age_description is null");
            return (Criteria) this;
        }

        public Criteria andAgeDescriptionIsNotNull() {
            addCriterion("age_description is not null");
            return (Criteria) this;
        }

        public Criteria andAgeDescriptionEqualTo(String value) {
            addCriterion("age_description =", value, "ageDescription");
            return (Criteria) this;
        }

        public Criteria andAgeDescriptionNotEqualTo(String value) {
            addCriterion("age_description <>", value, "ageDescription");
            return (Criteria) this;
        }

        public Criteria andAgeDescriptionGreaterThan(String value) {
            addCriterion("age_description >", value, "ageDescription");
            return (Criteria) this;
        }

        public Criteria andAgeDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("age_description >=", value, "ageDescription");
            return (Criteria) this;
        }

        public Criteria andAgeDescriptionLessThan(String value) {
            addCriterion("age_description <", value, "ageDescription");
            return (Criteria) this;
        }

        public Criteria andAgeDescriptionLessThanOrEqualTo(String value) {
            addCriterion("age_description <=", value, "ageDescription");
            return (Criteria) this;
        }

        public Criteria andAgeDescriptionLike(String value) {
            addCriterion("age_description like", value, "ageDescription");
            return (Criteria) this;
        }

        public Criteria andAgeDescriptionNotLike(String value) {
            addCriterion("age_description not like", value, "ageDescription");
            return (Criteria) this;
        }

        public Criteria andAgeDescriptionIn(List<String> values) {
            addCriterion("age_description in", values, "ageDescription");
            return (Criteria) this;
        }

        public Criteria andAgeDescriptionNotIn(List<String> values) {
            addCriterion("age_description not in", values, "ageDescription");
            return (Criteria) this;
        }

        public Criteria andAgeDescriptionBetween(String value1, String value2) {
            addCriterion("age_description between", value1, value2, "ageDescription");
            return (Criteria) this;
        }

        public Criteria andAgeDescriptionNotBetween(String value1, String value2) {
            addCriterion("age_description not between", value1, value2, "ageDescription");
            return (Criteria) this;
        }

        public Criteria andAreaIsNull() {
            addCriterion("area is null");
            return (Criteria) this;
        }

        public Criteria andAreaIsNotNull() {
            addCriterion("area is not null");
            return (Criteria) this;
        }

        public Criteria andAreaEqualTo(String value) {
            addCriterion("area =", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotEqualTo(String value) {
            addCriterion("area <>", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThan(String value) {
            addCriterion("area >", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThanOrEqualTo(String value) {
            addCriterion("area >=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThan(String value) {
            addCriterion("area <", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThanOrEqualTo(String value) {
            addCriterion("area <=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLike(String value) {
            addCriterion("area like", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotLike(String value) {
            addCriterion("area not like", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaIn(List<String> values) {
            addCriterion("area in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotIn(List<String> values) {
            addCriterion("area not in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaBetween(String value1, String value2) {
            addCriterion("area between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotBetween(String value1, String value2) {
            addCriterion("area not between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andPersonalDescriptionIsNull() {
            addCriterion("personal_description is null");
            return (Criteria) this;
        }

        public Criteria andPersonalDescriptionIsNotNull() {
            addCriterion("personal_description is not null");
            return (Criteria) this;
        }

        public Criteria andPersonalDescriptionEqualTo(String value) {
            addCriterion("personal_description =", value, "personalDescription");
            return (Criteria) this;
        }

        public Criteria andPersonalDescriptionNotEqualTo(String value) {
            addCriterion("personal_description <>", value, "personalDescription");
            return (Criteria) this;
        }

        public Criteria andPersonalDescriptionGreaterThan(String value) {
            addCriterion("personal_description >", value, "personalDescription");
            return (Criteria) this;
        }

        public Criteria andPersonalDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("personal_description >=", value, "personalDescription");
            return (Criteria) this;
        }

        public Criteria andPersonalDescriptionLessThan(String value) {
            addCriterion("personal_description <", value, "personalDescription");
            return (Criteria) this;
        }

        public Criteria andPersonalDescriptionLessThanOrEqualTo(String value) {
            addCriterion("personal_description <=", value, "personalDescription");
            return (Criteria) this;
        }

        public Criteria andPersonalDescriptionLike(String value) {
            addCriterion("personal_description like", value, "personalDescription");
            return (Criteria) this;
        }

        public Criteria andPersonalDescriptionNotLike(String value) {
            addCriterion("personal_description not like", value, "personalDescription");
            return (Criteria) this;
        }

        public Criteria andPersonalDescriptionIn(List<String> values) {
            addCriterion("personal_description in", values, "personalDescription");
            return (Criteria) this;
        }

        public Criteria andPersonalDescriptionNotIn(List<String> values) {
            addCriterion("personal_description not in", values, "personalDescription");
            return (Criteria) this;
        }

        public Criteria andPersonalDescriptionBetween(String value1, String value2) {
            addCriterion("personal_description between", value1, value2, "personalDescription");
            return (Criteria) this;
        }

        public Criteria andPersonalDescriptionNotBetween(String value1, String value2) {
            addCriterion("personal_description not between", value1, value2, "personalDescription");
            return (Criteria) this;
        }

        public Criteria andTotalListenIsNull() {
            addCriterion("total_listen is null");
            return (Criteria) this;
        }

        public Criteria andTotalListenIsNotNull() {
            addCriterion("total_listen is not null");
            return (Criteria) this;
        }

        public Criteria andTotalListenEqualTo(Integer value) {
            addCriterion("total_listen =", value, "totalListen");
            return (Criteria) this;
        }

        public Criteria andTotalListenNotEqualTo(Integer value) {
            addCriterion("total_listen <>", value, "totalListen");
            return (Criteria) this;
        }

        public Criteria andTotalListenGreaterThan(Integer value) {
            addCriterion("total_listen >", value, "totalListen");
            return (Criteria) this;
        }

        public Criteria andTotalListenGreaterThanOrEqualTo(Integer value) {
            addCriterion("total_listen >=", value, "totalListen");
            return (Criteria) this;
        }

        public Criteria andTotalListenLessThan(Integer value) {
            addCriterion("total_listen <", value, "totalListen");
            return (Criteria) this;
        }

        public Criteria andTotalListenLessThanOrEqualTo(Integer value) {
            addCriterion("total_listen <=", value, "totalListen");
            return (Criteria) this;
        }

        public Criteria andTotalListenIn(List<Integer> values) {
            addCriterion("total_listen in", values, "totalListen");
            return (Criteria) this;
        }

        public Criteria andTotalListenNotIn(List<Integer> values) {
            addCriterion("total_listen not in", values, "totalListen");
            return (Criteria) this;
        }

        public Criteria andTotalListenBetween(Integer value1, Integer value2) {
            addCriterion("total_listen between", value1, value2, "totalListen");
            return (Criteria) this;
        }

        public Criteria andTotalListenNotBetween(Integer value1, Integer value2) {
            addCriterion("total_listen not between", value1, value2, "totalListen");
            return (Criteria) this;
        }

        public Criteria andFolloweeCountIsNull() {
            addCriterion("followee_count is null");
            return (Criteria) this;
        }

        public Criteria andFolloweeCountIsNotNull() {
            addCriterion("followee_count is not null");
            return (Criteria) this;
        }

        public Criteria andFolloweeCountEqualTo(Integer value) {
            addCriterion("followee_count =", value, "followeeCount");
            return (Criteria) this;
        }

        public Criteria andFolloweeCountNotEqualTo(Integer value) {
            addCriterion("followee_count <>", value, "followeeCount");
            return (Criteria) this;
        }

        public Criteria andFolloweeCountGreaterThan(Integer value) {
            addCriterion("followee_count >", value, "followeeCount");
            return (Criteria) this;
        }

        public Criteria andFolloweeCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("followee_count >=", value, "followeeCount");
            return (Criteria) this;
        }

        public Criteria andFolloweeCountLessThan(Integer value) {
            addCriterion("followee_count <", value, "followeeCount");
            return (Criteria) this;
        }

        public Criteria andFolloweeCountLessThanOrEqualTo(Integer value) {
            addCriterion("followee_count <=", value, "followeeCount");
            return (Criteria) this;
        }

        public Criteria andFolloweeCountIn(List<Integer> values) {
            addCriterion("followee_count in", values, "followeeCount");
            return (Criteria) this;
        }

        public Criteria andFolloweeCountNotIn(List<Integer> values) {
            addCriterion("followee_count not in", values, "followeeCount");
            return (Criteria) this;
        }

        public Criteria andFolloweeCountBetween(Integer value1, Integer value2) {
            addCriterion("followee_count between", value1, value2, "followeeCount");
            return (Criteria) this;
        }

        public Criteria andFolloweeCountNotBetween(Integer value1, Integer value2) {
            addCriterion("followee_count not between", value1, value2, "followeeCount");
            return (Criteria) this;
        }

        public Criteria andFollowerCountIsNull() {
            addCriterion("follower_count is null");
            return (Criteria) this;
        }

        public Criteria andFollowerCountIsNotNull() {
            addCriterion("follower_count is not null");
            return (Criteria) this;
        }

        public Criteria andFollowerCountEqualTo(Integer value) {
            addCriterion("follower_count =", value, "followerCount");
            return (Criteria) this;
        }

        public Criteria andFollowerCountNotEqualTo(Integer value) {
            addCriterion("follower_count <>", value, "followerCount");
            return (Criteria) this;
        }

        public Criteria andFollowerCountGreaterThan(Integer value) {
            addCriterion("follower_count >", value, "followerCount");
            return (Criteria) this;
        }

        public Criteria andFollowerCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("follower_count >=", value, "followerCount");
            return (Criteria) this;
        }

        public Criteria andFollowerCountLessThan(Integer value) {
            addCriterion("follower_count <", value, "followerCount");
            return (Criteria) this;
        }

        public Criteria andFollowerCountLessThanOrEqualTo(Integer value) {
            addCriterion("follower_count <=", value, "followerCount");
            return (Criteria) this;
        }

        public Criteria andFollowerCountIn(List<Integer> values) {
            addCriterion("follower_count in", values, "followerCount");
            return (Criteria) this;
        }

        public Criteria andFollowerCountNotIn(List<Integer> values) {
            addCriterion("follower_count not in", values, "followerCount");
            return (Criteria) this;
        }

        public Criteria andFollowerCountBetween(Integer value1, Integer value2) {
            addCriterion("follower_count between", value1, value2, "followerCount");
            return (Criteria) this;
        }

        public Criteria andFollowerCountNotBetween(Integer value1, Integer value2) {
            addCriterion("follower_count not between", value1, value2, "followerCount");
            return (Criteria) this;
        }

        public Criteria andVipLevelIsNull() {
            addCriterion("vip_level is null");
            return (Criteria) this;
        }

        public Criteria andVipLevelIsNotNull() {
            addCriterion("vip_level is not null");
            return (Criteria) this;
        }

        public Criteria andVipLevelEqualTo(Byte value) {
            addCriterion("vip_level =", value, "vipLevel");
            return (Criteria) this;
        }

        public Criteria andVipLevelNotEqualTo(Byte value) {
            addCriterion("vip_level <>", value, "vipLevel");
            return (Criteria) this;
        }

        public Criteria andVipLevelGreaterThan(Byte value) {
            addCriterion("vip_level >", value, "vipLevel");
            return (Criteria) this;
        }

        public Criteria andVipLevelGreaterThanOrEqualTo(Byte value) {
            addCriterion("vip_level >=", value, "vipLevel");
            return (Criteria) this;
        }

        public Criteria andVipLevelLessThan(Byte value) {
            addCriterion("vip_level <", value, "vipLevel");
            return (Criteria) this;
        }

        public Criteria andVipLevelLessThanOrEqualTo(Byte value) {
            addCriterion("vip_level <=", value, "vipLevel");
            return (Criteria) this;
        }

        public Criteria andVipLevelIn(List<Byte> values) {
            addCriterion("vip_level in", values, "vipLevel");
            return (Criteria) this;
        }

        public Criteria andVipLevelNotIn(List<Byte> values) {
            addCriterion("vip_level not in", values, "vipLevel");
            return (Criteria) this;
        }

        public Criteria andVipLevelBetween(Byte value1, Byte value2) {
            addCriterion("vip_level between", value1, value2, "vipLevel");
            return (Criteria) this;
        }

        public Criteria andVipLevelNotBetween(Byte value1, Byte value2) {
            addCriterion("vip_level not between", value1, value2, "vipLevel");
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