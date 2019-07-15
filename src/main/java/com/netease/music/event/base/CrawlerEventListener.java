package com.netease.music.event.base;

import org.springframework.context.ApplicationListener;

public abstract class CrawlerEventListener<E extends CrawlerEvent> implements ApplicationListener<E> {
    @Override
    public void onApplicationEvent(final E event) {
        onAfsEvent(event);
    }

    public abstract void onAfsEvent(E e);
}
