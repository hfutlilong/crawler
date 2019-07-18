package com.netease.music.dao.po;

import com.netease.kaola.cs.utils.pagination.PaginationInfo;
import com.netease.music.entity.enums.BooleanIntEnum;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProxyPOExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected PaginationInfo pageInfo;

    public ProxyPOExample() {
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
        protected List<Criterion> httpProxyCriteria;

        protected List<Criterion> httpsProxyCriteria;

        protected List<Criterion> allCriteria;

        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
            httpProxyCriteria = new ArrayList<Criterion>();
            httpsProxyCriteria = new ArrayList<Criterion>();
        }

        public List<Criterion> getHttpProxyCriteria() {
            return httpProxyCriteria;
        }

        protected void addHttpProxyCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            httpProxyCriteria.add(new Criterion(condition, value, "com.netease.music.dao.typehandler.BooleanIntEnumHandler"));
            allCriteria = null;
        }

        protected void addHttpProxyCriterion(String condition, BooleanIntEnum value1, BooleanIntEnum value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            httpProxyCriteria.add(new Criterion(condition, value1, value2, "com.netease.music.dao.typehandler.BooleanIntEnumHandler"));
            allCriteria = null;
        }

        public List<Criterion> getHttpsProxyCriteria() {
            return httpsProxyCriteria;
        }

        protected void addHttpsProxyCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            httpsProxyCriteria.add(new Criterion(condition, value, "com.netease.music.dao.typehandler.BooleanIntEnumHandler"));
            allCriteria = null;
        }

        protected void addHttpsProxyCriterion(String condition, BooleanIntEnum value1, BooleanIntEnum value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            httpsProxyCriteria.add(new Criterion(condition, value1, value2, "com.netease.music.dao.typehandler.BooleanIntEnumHandler"));
            allCriteria = null;
        }

        public boolean isValid() {
            return criteria.size() > 0
                || httpProxyCriteria.size() > 0
                || httpsProxyCriteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            if (allCriteria == null) {
                allCriteria = new ArrayList<Criterion>();
                allCriteria.addAll(criteria);
                allCriteria.addAll(httpProxyCriteria);
                allCriteria.addAll(httpsProxyCriteria);
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

        public Criteria andIpIsNull() {
            addCriterion("ip is null");
            return (Criteria) this;
        }

        public Criteria andIpIsNotNull() {
            addCriterion("ip is not null");
            return (Criteria) this;
        }

        public Criteria andIpEqualTo(Integer value) {
            addCriterion("ip =", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotEqualTo(Integer value) {
            addCriterion("ip <>", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThan(Integer value) {
            addCriterion("ip >", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpGreaterThanOrEqualTo(Integer value) {
            addCriterion("ip >=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThan(Integer value) {
            addCriterion("ip <", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpLessThanOrEqualTo(Integer value) {
            addCriterion("ip <=", value, "ip");
            return (Criteria) this;
        }

        public Criteria andIpIn(List<Integer> values) {
            addCriterion("ip in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotIn(List<Integer> values) {
            addCriterion("ip not in", values, "ip");
            return (Criteria) this;
        }

        public Criteria andIpBetween(Integer value1, Integer value2) {
            addCriterion("ip between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andIpNotBetween(Integer value1, Integer value2) {
            addCriterion("ip not between", value1, value2, "ip");
            return (Criteria) this;
        }

        public Criteria andPortIsNull() {
            addCriterion("port is null");
            return (Criteria) this;
        }

        public Criteria andPortIsNotNull() {
            addCriterion("port is not null");
            return (Criteria) this;
        }

        public Criteria andPortEqualTo(Integer value) {
            addCriterion("port =", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortNotEqualTo(Integer value) {
            addCriterion("port <>", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortGreaterThan(Integer value) {
            addCriterion("port >", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortGreaterThanOrEqualTo(Integer value) {
            addCriterion("port >=", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortLessThan(Integer value) {
            addCriterion("port <", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortLessThanOrEqualTo(Integer value) {
            addCriterion("port <=", value, "port");
            return (Criteria) this;
        }

        public Criteria andPortIn(List<Integer> values) {
            addCriterion("port in", values, "port");
            return (Criteria) this;
        }

        public Criteria andPortNotIn(List<Integer> values) {
            addCriterion("port not in", values, "port");
            return (Criteria) this;
        }

        public Criteria andPortBetween(Integer value1, Integer value2) {
            addCriterion("port between", value1, value2, "port");
            return (Criteria) this;
        }

        public Criteria andPortNotBetween(Integer value1, Integer value2) {
            addCriterion("port not between", value1, value2, "port");
            return (Criteria) this;
        }

        public Criteria andIpStringIsNull() {
            addCriterion("ip_string is null");
            return (Criteria) this;
        }

        public Criteria andIpStringIsNotNull() {
            addCriterion("ip_string is not null");
            return (Criteria) this;
        }

        public Criteria andIpStringEqualTo(String value) {
            addCriterion("ip_string =", value, "ipString");
            return (Criteria) this;
        }

        public Criteria andIpStringNotEqualTo(String value) {
            addCriterion("ip_string <>", value, "ipString");
            return (Criteria) this;
        }

        public Criteria andIpStringGreaterThan(String value) {
            addCriterion("ip_string >", value, "ipString");
            return (Criteria) this;
        }

        public Criteria andIpStringGreaterThanOrEqualTo(String value) {
            addCriterion("ip_string >=", value, "ipString");
            return (Criteria) this;
        }

        public Criteria andIpStringLessThan(String value) {
            addCriterion("ip_string <", value, "ipString");
            return (Criteria) this;
        }

        public Criteria andIpStringLessThanOrEqualTo(String value) {
            addCriterion("ip_string <=", value, "ipString");
            return (Criteria) this;
        }

        public Criteria andIpStringLike(String value) {
            addCriterion("ip_string like", value, "ipString");
            return (Criteria) this;
        }

        public Criteria andIpStringNotLike(String value) {
            addCriterion("ip_string not like", value, "ipString");
            return (Criteria) this;
        }

        public Criteria andIpStringIn(List<String> values) {
            addCriterion("ip_string in", values, "ipString");
            return (Criteria) this;
        }

        public Criteria andIpStringNotIn(List<String> values) {
            addCriterion("ip_string not in", values, "ipString");
            return (Criteria) this;
        }

        public Criteria andIpStringBetween(String value1, String value2) {
            addCriterion("ip_string between", value1, value2, "ipString");
            return (Criteria) this;
        }

        public Criteria andIpStringNotBetween(String value1, String value2) {
            addCriterion("ip_string not between", value1, value2, "ipString");
            return (Criteria) this;
        }

        public Criteria andHttpProxyIsNull() {
            addCriterion("http_proxy is null");
            return (Criteria) this;
        }

        public Criteria andHttpProxyIsNotNull() {
            addCriterion("http_proxy is not null");
            return (Criteria) this;
        }

        public Criteria andHttpProxyEqualTo(BooleanIntEnum value) {
            addHttpProxyCriterion("http_proxy =", value, "httpProxy");
            return (Criteria) this;
        }

        public Criteria andHttpProxyNotEqualTo(BooleanIntEnum value) {
            addHttpProxyCriterion("http_proxy <>", value, "httpProxy");
            return (Criteria) this;
        }

        public Criteria andHttpProxyGreaterThan(BooleanIntEnum value) {
            addHttpProxyCriterion("http_proxy >", value, "httpProxy");
            return (Criteria) this;
        }

        public Criteria andHttpProxyGreaterThanOrEqualTo(BooleanIntEnum value) {
            addHttpProxyCriterion("http_proxy >=", value, "httpProxy");
            return (Criteria) this;
        }

        public Criteria andHttpProxyLessThan(BooleanIntEnum value) {
            addHttpProxyCriterion("http_proxy <", value, "httpProxy");
            return (Criteria) this;
        }

        public Criteria andHttpProxyLessThanOrEqualTo(BooleanIntEnum value) {
            addHttpProxyCriterion("http_proxy <=", value, "httpProxy");
            return (Criteria) this;
        }

        public Criteria andHttpProxyIn(List<BooleanIntEnum> values) {
            addHttpProxyCriterion("http_proxy in", values, "httpProxy");
            return (Criteria) this;
        }

        public Criteria andHttpProxyNotIn(List<BooleanIntEnum> values) {
            addHttpProxyCriterion("http_proxy not in", values, "httpProxy");
            return (Criteria) this;
        }

        public Criteria andHttpProxyBetween(BooleanIntEnum value1, BooleanIntEnum value2) {
            addHttpProxyCriterion("http_proxy between", value1, value2, "httpProxy");
            return (Criteria) this;
        }

        public Criteria andHttpProxyNotBetween(BooleanIntEnum value1, BooleanIntEnum value2) {
            addHttpProxyCriterion("http_proxy not between", value1, value2, "httpProxy");
            return (Criteria) this;
        }

        public Criteria andHttpsProxyIsNull() {
            addCriterion("https_proxy is null");
            return (Criteria) this;
        }

        public Criteria andHttpsProxyIsNotNull() {
            addCriterion("https_proxy is not null");
            return (Criteria) this;
        }

        public Criteria andHttpsProxyEqualTo(BooleanIntEnum value) {
            addHttpsProxyCriterion("https_proxy =", value, "httpsProxy");
            return (Criteria) this;
        }

        public Criteria andHttpsProxyNotEqualTo(BooleanIntEnum value) {
            addHttpsProxyCriterion("https_proxy <>", value, "httpsProxy");
            return (Criteria) this;
        }

        public Criteria andHttpsProxyGreaterThan(BooleanIntEnum value) {
            addHttpsProxyCriterion("https_proxy >", value, "httpsProxy");
            return (Criteria) this;
        }

        public Criteria andHttpsProxyGreaterThanOrEqualTo(BooleanIntEnum value) {
            addHttpsProxyCriterion("https_proxy >=", value, "httpsProxy");
            return (Criteria) this;
        }

        public Criteria andHttpsProxyLessThan(BooleanIntEnum value) {
            addHttpsProxyCriterion("https_proxy <", value, "httpsProxy");
            return (Criteria) this;
        }

        public Criteria andHttpsProxyLessThanOrEqualTo(BooleanIntEnum value) {
            addHttpsProxyCriterion("https_proxy <=", value, "httpsProxy");
            return (Criteria) this;
        }

        public Criteria andHttpsProxyIn(List<BooleanIntEnum> values) {
            addHttpsProxyCriterion("https_proxy in", values, "httpsProxy");
            return (Criteria) this;
        }

        public Criteria andHttpsProxyNotIn(List<BooleanIntEnum> values) {
            addHttpsProxyCriterion("https_proxy not in", values, "httpsProxy");
            return (Criteria) this;
        }

        public Criteria andHttpsProxyBetween(BooleanIntEnum value1, BooleanIntEnum value2) {
            addHttpsProxyCriterion("https_proxy between", value1, value2, "httpsProxy");
            return (Criteria) this;
        }

        public Criteria andHttpsProxyNotBetween(BooleanIntEnum value1, BooleanIntEnum value2) {
            addHttpsProxyCriterion("https_proxy not between", value1, value2, "httpsProxy");
            return (Criteria) this;
        }

        public Criteria andLocationInfoIsNull() {
            addCriterion("location_info is null");
            return (Criteria) this;
        }

        public Criteria andLocationInfoIsNotNull() {
            addCriterion("location_info is not null");
            return (Criteria) this;
        }

        public Criteria andLocationInfoEqualTo(String value) {
            addCriterion("location_info =", value, "locationInfo");
            return (Criteria) this;
        }

        public Criteria andLocationInfoNotEqualTo(String value) {
            addCriterion("location_info <>", value, "locationInfo");
            return (Criteria) this;
        }

        public Criteria andLocationInfoGreaterThan(String value) {
            addCriterion("location_info >", value, "locationInfo");
            return (Criteria) this;
        }

        public Criteria andLocationInfoGreaterThanOrEqualTo(String value) {
            addCriterion("location_info >=", value, "locationInfo");
            return (Criteria) this;
        }

        public Criteria andLocationInfoLessThan(String value) {
            addCriterion("location_info <", value, "locationInfo");
            return (Criteria) this;
        }

        public Criteria andLocationInfoLessThanOrEqualTo(String value) {
            addCriterion("location_info <=", value, "locationInfo");
            return (Criteria) this;
        }

        public Criteria andLocationInfoLike(String value) {
            addCriterion("location_info like", value, "locationInfo");
            return (Criteria) this;
        }

        public Criteria andLocationInfoNotLike(String value) {
            addCriterion("location_info not like", value, "locationInfo");
            return (Criteria) this;
        }

        public Criteria andLocationInfoIn(List<String> values) {
            addCriterion("location_info in", values, "locationInfo");
            return (Criteria) this;
        }

        public Criteria andLocationInfoNotIn(List<String> values) {
            addCriterion("location_info not in", values, "locationInfo");
            return (Criteria) this;
        }

        public Criteria andLocationInfoBetween(String value1, String value2) {
            addCriterion("location_info between", value1, value2, "locationInfo");
            return (Criteria) this;
        }

        public Criteria andLocationInfoNotBetween(String value1, String value2) {
            addCriterion("location_info not between", value1, value2, "locationInfo");
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