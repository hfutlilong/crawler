package com.netease.music.event.listener;

import com.netease.kaola.cs.utils.DateUtil;
import com.netease.music.common.log.LogConstant;
import com.netease.music.event.InitPlayListFinishEvent;
import com.netease.music.event.base.CrawlerEventListener;
import com.netease.music.service.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

/**
 * @Description 歌单爬取完成事件监听器
 * @Author lilong
 * @Date 2019-07-15 10:44
 */
@Component
public class InitPlayListFinishEventListener extends CrawlerEventListener<InitPlayListFinishEvent> {
    @Autowired
    private CrawlerService crawlerService;

    @Override
    public void onAfsEvent(InitPlayListFinishEvent initPlayListFinishEvent) {
        String eventTime = DateUtil.formatTimestamp(new Timestamp(initPlayListFinishEvent.getTimestamp()), DateUtil.H_24_FORMAT); // 时间发生时间
        // 歌单初始化完成之后就爬取歌单的播放量、收藏量等数据，同时更新歌单对应的歌曲、初始化歌曲表
        try {
            crawlerService.crawlingPlayList();
        } catch (InterruptedException e) {
            LogConstant.BUS.error("crawlingPlayList failed.");
        }
    }
}
