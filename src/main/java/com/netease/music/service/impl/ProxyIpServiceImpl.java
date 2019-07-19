package com.netease.music.service.impl;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.netease.kaola.cs.utils.IpUtil;
import com.netease.music.common.constant.CrawlerConstant;
import com.netease.music.common.log.LogConstant;
import com.netease.music.dao.mapper.ProxyPOMapperExt;
import com.netease.music.dao.po.ProxyPO;
import com.netease.music.dao.po.ProxyPOExample;
import com.netease.music.entity.bo.ProxyBO;
import com.netease.music.entity.enums.BooleanIntEnum;
import com.netease.music.service.ProxyIpService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description 上网代理
 * @Author lilong
 * @Date 2019-07-18 15:02
 */
@Service
public class ProxyIpServiceImpl implements ProxyIpService {
    @Autowired
    private ProxyPOMapperExt proxyPOMapper;

    private Lock httpProxyLock = new ReentrantLock();

    private Condition httpProxyCondition = httpProxyLock.newCondition();

    private Lock httpsProxyLock = new ReentrantLock();

    private Condition httpsProxyCondition = httpsProxyLock.newCondition();

    // private static final ThreadPoolExecutor cleanProxyExecutor = new ThreadPoolExecutor(2, 4, 30, TimeUnit.SECONDS,
    // new LinkedBlockingQueue<>(50000), new ThreadFactoryBuilder().setNameFormat("cleanProxyExecutor-%d").build(),
    // new RejectedExecutionHandler() {
    // @Override
    // public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
    // LogConstant.BUS.error("cleanProxyExecutor reject execute.");
    // }
    // });

    private void cleanProxy() {
        LogConstant.BUS.info("start clean proxy...");
        ProxyPOExample proxyPOExample = new ProxyPOExample();
        List<ProxyPO> proxyPOList = proxyPOMapper.selectByExample(proxyPOExample);
        if (CollectionUtils.isEmpty(proxyPOList)) {
            LogConstant.BUS.info("No proxy ip in db.");
            return;
        }

        for (ProxyPO proxyPO : proxyPOList) {
            // cleanProxyExecutor.execute(() -> {
            Integer ipInt = proxyPO.getIp();
            String ip = proxyPO.getIpString();
            Integer port = proxyPO.getPort();

            ProxyPOExample proxyPOExampleNew = new ProxyPOExample();
            proxyPOExampleNew.createCriteria().andIpEqualTo(ipInt).andPortEqualTo(port);

            if (BooleanIntEnum.TRUE == proxyPO.getHttpsProxy() && !checkProxyHttpsPass(ip, port)) {
                proxyPO.setHttpsProxy(BooleanIntEnum.FALSE);
            }

            if (BooleanIntEnum.TRUE == proxyPO.getHttpProxy() && !checkProxyHttpPass(ip, port)) {
                proxyPO.setHttpProxy(BooleanIntEnum.FALSE);
            }

            if (BooleanIntEnum.FALSE == proxyPO.getHttpProxy() && BooleanIntEnum.FALSE == proxyPO.getHttpsProxy()) {
                proxyPOMapper.deleteByExample(proxyPOExampleNew);
            }
            // });
        }
        LogConstant.BUS.info("end clean proxy.");
    }

    /**
     * 从免费网站爬取代理ip
     */
    @Override
    @Async
    public void refreshProxyIp() {
        LogConstant.BUS.info("start clean unavailable proxy in db...");
        cleanProxy();
        LogConstant.BUS.info("end clean unavailable proxy in db.");
    }

    @Override
    @Async
    public void addProxyIp() {
        LogConstant.BUS.info("start addProxyIp...");
        ScheduledExecutorService schedule = Executors.newSingleThreadScheduledExecutor();
        try {
            schedule.scheduleWithFixedDelay(() -> {
                try {
                    cleanProxy();
                    saveProxyFromXici();
                } catch (Exception e) {
                    LogConstant.BUS.error("saveProxyFromXici failed");
                }
            }, 0, 1, TimeUnit.HOURS);

        } catch (Exception e) {
            LogConstant.BUS.error("addProxyIp failed.", e);
        }
        LogConstant.BUS.info("end addProxyIp");
    }

    /**
     * 西拉免费代理IP http://www.xiladaili.com/gaoni/
     */
    private void saveProxyFromXila() throws InterruptedException {
        for (int i = 1; i <= 2000; i++) {
            String url = CrawlerConstant.getProxyWebsiteXila(i);
            try {
                Document doc = getDocByHttpProxy(url);
                if (doc == null) {
                    LogConstant.BUS.info("doc of url {} is null.", url);
                    Thread.sleep(60000);
                    continue;
                }
                Elements ipElements = doc.selectFirst("table.fl-table").selectFirst("tbody").select("tr");
                if (ipElements == null) {
                    LogConstant.BUS.info("ipElements of url {} is null.", url);
                    continue;
                }

                for (Element ipElement : ipElements) {
                    Elements ipDetailElements = ipElement.select("td");
                    if (ipDetailElements == null) {
                        continue;
                    }
                    String location = ipDetailElements.get(3).text();
                    String ipPort = ipDetailElements.get(0).text();
                    if (StringUtils.isNotBlank(ipPort)) {
                        String proxyType = ipDetailElements.get(1).text();
                        if (StringUtils.isBlank(proxyType)) {
                            continue;
                        }

                        ProxyPO proxyPO = new ProxyPO();
                        proxyPO.setLocationInfo(location);
                        String[] ipSplitArr = ipPort.split(":");
                        if (ipSplitArr.length == 2) {
                            String ip = ipSplitArr[0];
                            int port = Integer.valueOf(ipSplitArr[1]);
                            proxyPO.setIp(IpUtil.ip2Int(ip));
                            proxyPO.setPort(port);
                            proxyPO.setIpString(ip);
                        }

                        if (proxyType.toLowerCase().contains("https")) {
                            if (checkProxyHttpsPass(proxyPO.getIpString(), proxyPO.getPort())) {
                                proxyPO.setHttpsProxy(BooleanIntEnum.TRUE);
                            }
                        }

                        if (proxyType.toLowerCase().equals("http代理") || proxyType.toLowerCase().contains("http,")) {
                            if (checkProxyHttpPass(proxyPO.getIpString(), proxyPO.getPort())) {
                                proxyPO.setHttpProxy(BooleanIntEnum.TRUE);
                            }
                        }

                        if (BooleanIntEnum.TRUE == proxyPO.getHttpProxy()
                                || BooleanIntEnum.TRUE == proxyPO.getHttpsProxy()) {
                            proxyPOMapper.insertOnDuplicateUpdate(proxyPO);
                        }
                    }
                }

                LogConstant.BUS.info("crawling url {} success.", url);
            } catch (Exception e) {
                LogConstant.BUS.info("crawling url {} failed.", url, e);
                Thread.sleep(60000);
            }

            Thread.sleep(3000);
        }
        LogConstant.BUS.info("save proxy from \"http://www.xiladaili.com/gaoni/\" end with success.");
    }

    /**
     * 快代理 https://www.kuaidaili.com/free/
     */
    private void saveProxyFromKuai() throws InterruptedException {
        for (int i = 1; i <= 2000; i++) {
            String url = CrawlerConstant.getProxyWebsiteKuai(i);
            try {
                Document doc = getDocByHttpProxy(url);
                if (doc == null) {
                    LogConstant.BUS.info("doc of url {} is null.", url);
                    Thread.sleep(60000);
                    continue;
                }
                Elements ipElements = doc.selectFirst("div#list")
                        .selectFirst("table.table.table-bordered.table-striped").selectFirst("tbody").select("tr");
                if (ipElements == null) {
                    LogConstant.BUS.info("ipElements of url {} is null.", url);
                    continue;
                }

                for (Element ipElement : ipElements) {
                    Elements ipDetailElements = ipElement.select("td");
                    if (ipDetailElements == null) {
                        continue;
                    }

                    String ip = ipDetailElements.get(0).text();
                    int port = Integer.valueOf(ipDetailElements.get(1).text());
                    String proxyType = ipDetailElements.get(3).text();
                    String location = ipDetailElements.get(4).text();

                    boolean checkPass = false;
                    if (proxyType.trim().toLowerCase().equals("http")) {
                        checkPass = checkProxyHttpPass(ip, port);
                    } else if (proxyType.trim().toLowerCase().equals("https")) {
                        checkPass = checkProxyHttpsPass(ip, port);
                    }
                    if (checkPass) {
                        ProxyPO proxyPO = new ProxyPO();
                        proxyPO.setIp(IpUtil.ip2Int(ip));
                        proxyPO.setPort(port);
                        proxyPO.setIpString(ip);
                        proxyPO.setLocationInfo(location);
                        if (proxyType.trim().toLowerCase().equals("http")) {
                            proxyPO.setHttpProxy(BooleanIntEnum.TRUE);
                        } else if (proxyType.trim().toLowerCase().equals("https")) {
                            proxyPO.setHttpsProxy(BooleanIntEnum.TRUE);
                        }
                        proxyPOMapper.insertOnDuplicateUpdate(proxyPO);
                    }
                }

                LogConstant.BUS.info("crawling url {} success.", url);
            } catch (Exception e) {
                LogConstant.BUS.info("crawling url {} failed.", url, e);
                Thread.sleep(60000);
            }

            Thread.sleep(3000);
        }
        LogConstant.BUS.info("save proxy from \"http://www.xiladaili.com/gaoni/\" end with success.");
    }

    /**
     * 全网代理ip
     */
    private void saveProxyFromGoubanjia() {
        String url = CrawlerConstant.PROXY_WEBSITE_GOUBANJIA;
        try {
            Document doc = getDocByHttpProxy(url);
            if (doc == null) {
                LogConstant.BUS.info("doc of url {} is null.", url);
                return;
            }
            Elements ipElements = doc.selectFirst("table.table.table-hover").selectFirst("tbody").select("tr");
            if (ipElements == null) {
                LogConstant.BUS.info("ipElements of url {} is null.", url);
                return;
            }

            for (Element ipElement : ipElements) {
                Elements ipDetailElements = ipElement.select("td");
                if (ipDetailElements == null) {
                    continue;
                }

                ProxyPO proxyPO = new ProxyPO();

                // ip:port
                Element ipPortElement = ipDetailElements.get(0);
                Elements ipPortSpans = ipPortElement.select("span");
                StringBuilder ipPortSb = new StringBuilder();
                for (Element ipPortSpan : ipPortSpans) {
                    ipPortSb.append(ipPortSpan.text());
                }
                String ipPort = ipPortSb.toString();
                if (StringUtils.isBlank(ipPort)) {
                    continue;
                }

                String ip = null;
                int port = 0;

                String[] ipSplitArr = ipPort.split(":");
                if (ipSplitArr.length == 2) {
                    ip = ipSplitArr[0];
                    port = Integer.valueOf(ipSplitArr[1]);
                    proxyPO.setIp(IpUtil.ip2Int(ip));
                    proxyPO.setPort(port);
                    proxyPO.setIpString(ip);
                }

                // 类型
                String proxyType = ipDetailElements.get(2).selectFirst("a.href").text();

                // 地址
                Elements locationElements = ipDetailElements.get(3).select("a.href");
                List<String> locationList = new ArrayList<>();
                for (Element locationElement : locationElements) {
                    locationList.add(locationElement.text());
                }
                String location = StringUtils.join(locationList, " ");
                proxyPO.setLocationInfo(location);

                boolean checkPass = false;
                if (proxyType.trim().toLowerCase().equals("http")) {
                    checkPass = checkProxyHttpPass(ip, port);
                    if (checkPass) {
                        proxyPO.setHttpProxy(BooleanIntEnum.TRUE);
                    }
                } else if (proxyType.trim().toLowerCase().equals("https")) {
                    checkPass = checkProxyHttpsPass(ip, port);
                    if (checkPass) {
                        proxyPO.setHttpsProxy(BooleanIntEnum.TRUE);
                    }
                }
                if (checkPass) {
                    proxyPOMapper.insertOnDuplicateUpdate(proxyPO);
                }
            }
        } catch (Exception e) {
            LogConstant.BUS.error("saveProxyFromGoubanjia failed:", e);
        }
    }

    /**
     * 国内高匿代理IP https://www.xicidaili.com/nn/1
     */
    private void saveProxyFromXici() throws InterruptedException {
        for (int i = 1; i <= 10; i++) {
            String url = CrawlerConstant.getProxyWebsiteXici(i);
            try {
                Document doc = getDoc(url);
                if (doc == null) {
                    LogConstant.BUS.info("doc of url {} is null.", url);
                    Thread.sleep(60000);
                    continue;
                }
                Elements ipElements = doc.selectFirst("table#ip_list").selectFirst("tbody").select("tr");
                if (ipElements == null) {
                    LogConstant.BUS.info("ipElements of url {} is null.", url);
                    continue;
                }

                for (Element ipElement : ipElements) {
                    try {
                        Elements ipDetailElements = ipElement.select("td");
                        if (ipDetailElements == null) {
                            continue;
                        }

                        String ip = ipDetailElements.get(1).text();
                        if (StringUtils.isBlank(ip) || !ip.matches("\\d+\\.\\d+\\.\\d+\\.\\d+")) {
                            continue;
                        }

                        int port = Integer.valueOf(ipDetailElements.get(2).text());

                        String location = null;
                        Element locationElement = ipDetailElements.get(3).selectFirst("a");
                        if (locationElement != null) {
                            location = locationElement.text();
                        }

                        String proxyType = ipDetailElements.get(5).text();

                        boolean checkPass = false;
                        if (proxyType.trim().toLowerCase().equals("http")) {
                            checkPass = checkProxyHttpPass(ip, port);
                        } else if (proxyType.trim().toLowerCase().equals("https")) {
                            checkPass = checkProxyHttpsPass(ip, port);
                        }
                        if (checkPass) {
                            ProxyPO proxyPO = new ProxyPO();
                            proxyPO.setIp(IpUtil.ip2Int(ip));
                            proxyPO.setPort(port);
                            proxyPO.setIpString(ip);
                            proxyPO.setLocationInfo(location);
                            if (proxyType.trim().toLowerCase().equals("http")) {
                                proxyPO.setHttpProxy(BooleanIntEnum.TRUE);
                            } else if (proxyType.trim().toLowerCase().equals("https")) {
                                proxyPO.setHttpsProxy(BooleanIntEnum.TRUE);
                            }
                            proxyPOMapper.insertOnDuplicateUpdate(proxyPO);
                        }

                    } catch (Exception e) {
                        LogConstant.BUS.error("crawling url {} failed.", url, e);
                    }
                }

                LogConstant.BUS.info("crawling url {} success.", url);
            } catch (Exception e) {
                LogConstant.BUS.error("crawling url {} failed.", url, e);
                Thread.sleep(30000);
            }

            Thread.sleep(30000);
        }
        LogConstant.BUS.info("save proxy from \"https://www.xicidaili.com\" end with success.");
    }

    @Override
    public ProxyBO getAvailableHttpsProxy() throws InterruptedException {
        ProxyBO proxyBO;
        httpsProxyLock.lock();
        try {
            while ((proxyBO = getAvailableHttpsProxyOnce()) == null) {
                LogConstant.BUS.info("no available https proxy, wait for db update.");
                cleanProxy(); // 清理下不可用的代理
                httpsProxyCondition.await();
            }
        } finally {
            httpsProxyLock.unlock();
        }

        return proxyBO;
    }

    public ProxyBO getAvailableHttpsProxyOnce() {
        // 查数据库
        ProxyPOExample proxyPOExample = new ProxyPOExample();
        proxyPOExample.createCriteria().andHttpsProxyEqualTo(BooleanIntEnum.TRUE);
        List<ProxyPO> proxyPOList = proxyPOMapper.selectByExample(proxyPOExample);
        if (CollectionUtils.isEmpty(proxyPOList)) {
            return null;
        }

        for (int i = 0; i < 10; i++) {
            ProxyPO proxyPO = proxyPOList.get(new Random().nextInt(proxyPOList.size()));
            if (proxyPO == null) {
                return null;
            }

            String ip = IpUtil.int2Ip(proxyPO.getIp());
            int port = proxyPO.getPort();

            if (checkProxyHttpsPass(ip, port)) {
                ProxyBO proxyBO = new ProxyBO();
                proxyBO.setIp(ip);
                proxyBO.setPort(port);
                return proxyBO;
            }
        }

        // 尝试10次都没成功，清理数据库、重新获取
        return null;
    }

    @Override
    public ProxyBO getAvailableHttpProxy() throws InterruptedException {
        ProxyBO proxyBO = null;
        httpProxyLock.lock();
        try {
            while ((proxyBO = getAvailableHttpProxyOnce()) == null) {
                LogConstant.BUS.info("no available http proxy, wait for db update.");
                cleanProxy(); // 清理下不可用的代理
                httpProxyCondition.await();
            }
        } finally {
            httpProxyLock.unlock();
        }

        return proxyBO;
    }

    private ProxyBO getAvailableHttpProxyOnce() {
        // 查数据库
        ProxyPOExample proxyPOExample = new ProxyPOExample();
        proxyPOExample.createCriteria().andHttpProxyEqualTo(BooleanIntEnum.TRUE);
        List<ProxyPO> proxyPOList = proxyPOMapper.selectByExample(proxyPOExample);
        if (CollectionUtils.isEmpty(proxyPOList)) {
            return null;
        }

        for (int i = 0; i < 10; i++) {
            ProxyPO proxyPO = proxyPOList.get(new Random().nextInt(proxyPOList.size()));
            if (proxyPO == null) {
                return null;
            }

            String ip = IpUtil.int2Ip(proxyPO.getIp());
            int port = proxyPO.getPort();

            if (checkProxyHttpPass(ip, port)) {
                ProxyBO proxyBO = new ProxyBO();
                proxyBO.setIp(ip);
                proxyBO.setPort(port);
                return proxyBO;
            }
        }

        return null;
    }

    private boolean checkProxyHttpsPass(String ip, int port) {
        try {
            Document baiduDoc = Jsoup.connect("https://www.baidu.com").userAgent(getUserAgentRandom()).proxy(ip, port)
                    .timeout(3000).get();
            if (baiduDoc == null) {
                return false;
            }
            Elements inputElements = baiduDoc.select("input#su.bg.s_btn");
            if (inputElements == null) {
                return false;
            }

            Document doc = Jsoup.connect("https://music.163.com/discover/playlist").userAgent(getUserAgentRandom())
                    .proxy(ip, port).timeout(5000).get();
            if (doc == null) {
                return false;
            }
            Elements playListElements = doc.select("ul#m-pl-container");
            if (playListElements == null) {
                return false;
            }

            return true;
        } catch (Exception e) {
            LogConstant.BUS.info("checkProxyHttpsPass failed, ip={}, port={}, exception info:{}.", ip, port,
                    e.getMessage());
            return false;
        }
    }

    private boolean checkProxyHttpPass(String ip, int port) {
        try {
            String url = CrawlerConstant.getSongCommentUrl(1374061028L, 1, 0);
            String musicComment = Jsoup.connect(url).userAgent(getUserAgentRandom()).proxy(ip, port)
                    .ignoreContentType(true).execute().body();
            return StringUtils.isNotBlank(musicComment);
        } catch (Exception e) {
            LogConstant.BUS.info("checkProxyHttpPass failed, ip={},port={}, exception info:{}", ip, port,
                    e.getMessage());
            return false;
        }
    }

    private String getUserAgentRandom() {
        List<String> userAgentList = CrawlerConstant.USER_AGENT_LIST;
        return userAgentList.get(new Random().nextInt(userAgentList.size()));
    }

    @Override
    public Document getDoc(String url) {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            LogConstant.BUS.error("getDoc connect url {} failed with IOException.", url);
            return null;
        }
    }

    @Override
    public Document getDocByHttpsProxy(String url) throws InterruptedException {
        ProxyBO proxyBO = getAvailableHttpsProxy();
        if (proxyBO == null) {
            LogConstant.BUS.error("getDocByHttpsProxy failed, no available proxy...");
            return null;
        }

        try {
            return Jsoup.connect(url).userAgent(getUserAgentRandom()).proxy(proxyBO.getIp(), proxyBO.getPort()).get();
        } catch (IOException e) {
            LogConstant.BUS.error("getDocByHttpsProxy connect url {} failed with IOException.", url);
            return null;
        }
    }

    private Document getDocByHttpProxy(String url) throws InterruptedException {
        ProxyBO proxyBO = getAvailableHttpProxy();
        if (proxyBO == null) {
            LogConstant.BUS.error("getDocByHttpProxy failed, no available proxy...");
            return null;
        }

        try {
            return Jsoup.connect(url).userAgent(getUserAgentRandom()).proxy(proxyBO.getIp(), proxyBO.getPort()).get();
        } catch (IOException e) {
            LogConstant.BUS.error("getDocByHttpProxy connect url {} failed with IOException.", url);
            return null;
        }
    }

    @Override
    public String getJsonResponseByHttpProxy(String url) throws InterruptedException {
        ProxyBO proxyBO = getAvailableHttpProxy();
        if (proxyBO == null) {
            LogConstant.BUS.error("getJsonResponseByProxy failed, no available proxy...");
            return null;
        }

        try {
            return Jsoup.connect(url).userAgent(getUserAgentRandom()).proxy(proxyBO.getIp(), proxyBO.getPort())
                    .ignoreContentType(true).execute().body();
        } catch (Exception e) {
            LogConstant.BUS.error("connect url {} failed:", url, e);
            return null;
        }
    }

    @Override
    @Async
    public void updateProxyIp(String proxyIps) {
        if (StringUtils.isBlank(proxyIps)) {
            LogConstant.BUS.error("proxyIps is blank.");
            return;
        }

        try {
            String[] proxyIpArr = proxyIps.split("\n|\n|\r\n");
            if (proxyIpArr.length == 0) {
                LogConstant.BUS.error("proxyIps.split empty, proxyIps={}.", proxyIps);
                return;
            }

            // "183.146.213.198:80\t高匿\thttps\t中国 浙江 金华"
            Pattern p = Pattern.compile("^(\\d+\\.\\d+\\.\\d+\\.\\d+):(\\d+).*(https?)(.*)$");

            for (String proxyIp : proxyIpArr) {
                if (StringUtils.isBlank(proxyIp)) {
                    continue;
                }

                String ip = null;
                int port = 80;
                String type = null;
                String location = null;

                Matcher m = p.matcher(proxyIp);
                if (m.find()) {
                    ip = m.group(1);
                    port = Integer.valueOf(m.group(2));
                    type = m.group(3);
                    location = m.group(4);
                }

                ProxyPO proxyPO = new ProxyPO();
                proxyPO.setIp(StringUtils.isNotBlank(ip) ? IpUtil.ip2Int(ip) : null);
                proxyPO.setPort(port);
                proxyPO.setIpString(ip);
                proxyPO.setLocationInfo(location);
                if (StringUtils.isNotBlank(type) && type.toLowerCase().equals("http")) {
                    if (checkProxyHttpPass(ip, port)) {
                        proxyPO.setHttpProxy(BooleanIntEnum.TRUE);

                        httpProxyLock.lock();
                        try {
                            httpProxyCondition.signalAll();
                            LogConstant.BUS.info("httpProxyCondition.signalAll success.");
                        } catch (Exception e) {
                            LogConstant.BUS.error("httpProxyCondition.notifyAll failed:", e);
                        } finally {
                            httpProxyLock.unlock();
                        }
                    }
                }
                if (StringUtils.isNotBlank(type) && type.toLowerCase().equals("https")) {
                    if (checkProxyHttpsPass(ip, port)) {
                        proxyPO.setHttpsProxy(BooleanIntEnum.TRUE);

                        httpsProxyLock.lock();
                        try {
                            httpsProxyCondition.signalAll();
                            LogConstant.BUS.info("httpsProxyCondition.signalAll success.");
                        } catch (Exception e) {
                            LogConstant.BUS.error("httpsProxyCondition.signalAll failed:", e);
                        } finally {
                            httpsProxyLock.unlock();
                        }
                    }
                }

                if (BooleanIntEnum.TRUE == proxyPO.getHttpProxy() || BooleanIntEnum.TRUE == proxyPO.getHttpsProxy()) {
                    proxyPOMapper.insertOnDuplicateUpdate(proxyPO);
                }
            }
        } catch (Exception e) {
            LogConstant.BUS.error("updateProxyIp failed, proxyIps={}.", proxyIps, e);
        }

        notifyAllCondition();
    }

    /**
     * 唤醒等待队列中的线程
     */
    private void notifyAllCondition() {
        // 通知等待的线程
        ProxyPOExample httpProxyPOExample = new ProxyPOExample();
        httpProxyPOExample.createCriteria().andHttpProxyEqualTo(BooleanIntEnum.TRUE);
        int httpProxyCount = proxyPOMapper.countByExample(httpProxyPOExample);
        if (httpProxyCount > 0) {
            httpProxyLock.lock();
            try {
                httpProxyCondition.signalAll();
                LogConstant.BUS.info("httpProxyCondition.signalAll success.");
            } catch (Exception e) {
                LogConstant.BUS.error("httpProxyCondition.notifyAll failed:", e);
            } finally {
                httpProxyLock.unlock();
            }
        }

        ProxyPOExample httpsProxyPOExample = new ProxyPOExample();
        httpProxyPOExample.createCriteria().andHttpsProxyEqualTo(BooleanIntEnum.TRUE);
        int httpsProxyCount = proxyPOMapper.countByExample(httpsProxyPOExample);
        if (httpsProxyCount > 0) {
            httpsProxyLock.lock();
            try {
                httpsProxyCondition.signalAll();
                LogConstant.BUS.info("httpsProxyCondition.signalAll success.");
            } catch (Exception e) {
                LogConstant.BUS.error("httpsProxyCondition.signalAll failed:", e);
            } finally {
                httpsProxyLock.unlock();
            }
        }
    }
}
