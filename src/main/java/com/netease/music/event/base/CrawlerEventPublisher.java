package com.netease.music.event.base;

import com.netease.music.utils.SpringContextUtil;

public class CrawlerEventPublisher {
    public static void publish(CrawlerEvent crawlerEvent) {
        SpringContextUtil.getApplicationContext().publishEvent(crawlerEvent);
    }
}
