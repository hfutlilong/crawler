package com.netease.music.dao.po;

import java.io.Serializable;

public class UserAgentPO implements Serializable {
    private Long id;

    private String userAgent;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent == null ? null : userAgent.trim();
    }
}