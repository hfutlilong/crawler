package com.netease.music.event.base;

import com.alibaba.fastjson.JSON;
import org.springframework.context.ApplicationEvent;

public abstract class CrawlerEvent extends ApplicationEvent {
    public CrawlerEvent() {
        super(0);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
