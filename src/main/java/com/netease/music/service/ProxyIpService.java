package com.netease.music.service;

import com.netease.music.entity.bo.ProxyBO;
import org.jsoup.nodes.Document;

public interface ProxyIpService {
    /**
     * 定时更新代理
     */
    void refreshProxyIpSchedule();
    /**
     * 获取一个可用的https代理
     * @return
     */
    ProxyBO getAvailableHttpsProxy() throws InterruptedException;

    /**
     * 获取一个可用的http代理
     * @return
     */
    ProxyBO getAvailableHttpProxy() throws InterruptedException;

    Document getDocByHttpsProxy(String url) throws InterruptedException;

    String getJsonResponseByHttpProxy(String url) throws InterruptedException;
}
