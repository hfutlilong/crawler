package com.netease.music.dao.po;

import com.netease.music.entity.enums.BooleanIntEnum;
import java.io.Serializable;
import java.util.Date;

public class ProxyPO implements Serializable {
    private Long id;

    private Integer ip;

    private Integer port;

    private String ipString;

    private BooleanIntEnum httpProxy;

    private BooleanIntEnum httpsProxy;

    private String locationInfo;

    private Date dbUpdateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIp() {
        return ip;
    }

    public void setIp(Integer ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getIpString() {
        return ipString;
    }

    public void setIpString(String ipString) {
        this.ipString = ipString == null ? null : ipString.trim();
    }

    public BooleanIntEnum getHttpProxy() {
        return httpProxy;
    }

    public void setHttpProxy(BooleanIntEnum httpProxy) {
        this.httpProxy = httpProxy;
    }

    public BooleanIntEnum getHttpsProxy() {
        return httpsProxy;
    }

    public void setHttpsProxy(BooleanIntEnum httpsProxy) {
        this.httpsProxy = httpsProxy;
    }

    public String getLocationInfo() {
        return locationInfo;
    }

    public void setLocationInfo(String locationInfo) {
        this.locationInfo = locationInfo == null ? null : locationInfo.trim();
    }

    public Date getDbUpdateTime() {
        return dbUpdateTime;
    }

    public void setDbUpdateTime(Date dbUpdateTime) {
        this.dbUpdateTime = dbUpdateTime;
    }
}