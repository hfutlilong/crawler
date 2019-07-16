package com.netease.music.service;

import com.netease.music.base.BaseTest;
import com.netease.music.dao.mapper.PlayListPOMapper;
import com.netease.music.dao.po.PlayListPO;
import com.netease.music.dao.po.PlayListPOExample;
import com.netease.music.service.impl.CrawlerServiceImpl;
import com.netease.music.utils.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class CrawlerServiceTest extends BaseTest {
    @Autowired
    private PlayListPOMapper playListPOMapper;

    @Test
    public void testDoCrawlingPlayList() {
        CrawlerServiceImpl crawlerServiceImpl = SpringContextUtil.getBean(CrawlerServiceImpl.class);
        PlayListPOExample example = new PlayListPOExample();
        example.createCriteria().andResourceIdEqualTo(2829816518L);
        PlayListPO playListPO = playListPOMapper.selectByExample(example).get(0);
        try {
            Method method = CrawlerServiceImpl.class.getDeclaredMethod("doCrawlingPlayList", PlayListPO.class);
            method.setAccessible(true);
            method.invoke(crawlerServiceImpl, playListPO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
