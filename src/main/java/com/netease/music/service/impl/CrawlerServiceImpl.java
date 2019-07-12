package com.netease.music.service.impl;

import com.alibaba.fastjson.JSON;
import com.netease.music.entity.constant.CrawlerConstant;
import com.netease.music.entity.constant.LogConstant;
import com.netease.music.service.CrawlerService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CrawlerServiceImpl implements CrawlerService {

    @Override
    @Async
    public void autoCrawling() {
        // 获取全部歌单类别
        List<String> categoryNameList = getAllCategoryNames();
        LogConstant.BUS.info("all categories: {}.", JSON.toJSONString(categoryNameList));

        // 遍历每一个类别下的所有歌单
        if (CollectionUtils.isEmpty(categoryNameList)) {
            LogConstant.BUS.error("categoryNameList is empty.");
            return;
        }

        for (String categoryName : categoryNameList) {
            getPlayListIdOneCategory(categoryName);
        }
    }

    @Override
    public List<Long> getPlayListIdOneCategory(String categoryName) {
        if (StringUtils.isBlank(categoryName)) {
            LogConstant.BUS.error("crawlingWithCategory failed, param categoryName cannot be blank.");
            return null;
        }

        int offset = 0;
        int limit = CrawlerConstant.DEFAULT_PAGE_SIZE;

        // 歌单页url
        String playListsUrl = CrawlerConstant.getPlayListsUrl(categoryName, limit, offset);
        if (StringUtils.isBlank(playListsUrl)) {
            LogConstant.BUS.error("crawlingWithCategory failed, playListsUrl is blank, .categoryName={}.", categoryName);
            return null;
        }

        List<Long> playListIdOneCategory = new ArrayList<>();

        for (;;) {
            List<Long> playListOnePage = getPlayListIdOnePage(playListsUrl);
            if (CollectionUtils.isEmpty(playListOnePage)) {
                break;
            }
            playListIdOneCategory.addAll(playListOnePage);
        }

        return playListIdOneCategory.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<Long> getPlayListIdOnePage(String url) {
        return null;
    }

    /**
     * 获取所有类别
     * 
     * @return
     */
    private List<String> getAllCategoryNames() {
        List<String> categoryNameList = new ArrayList<>();

        String startUrl = CrawlerConstant.START_URL;
        Document doc = null;
        try {
            doc = Jsoup.connect(startUrl).get();
        } catch (IOException e) {
            LogConstant.BUS.error("cannot read url:{}.", startUrl, e);
        }

        if (doc == null) {
            LogConstant.BUS.info("document of url {} is null.", startUrl);
            return null;
        }

        Elements categoryElements = doc.select("div#cateListBox");
        if (categoryElements == null) {
            LogConstant.BUS.info("categoryElements is null.");
            return null;
        }

        Element categoryElement = categoryElements.get(0);
        if (categoryElement == null) {
            LogConstant.BUS.info("categoryElement is null.");
            return null;
        }

        Elements categoryUrlElements = categoryElement.select("a[href]");
        for (Element categoryUrlElement : categoryUrlElements) {
            String categoryUrl = categoryUrlElement.attr("href");
            if (StringUtils.isNotBlank(categoryUrl) && categoryUrl.startsWith("/discover/playlist/")) {
                String categoryName = categoryUrlElement.attr("data-cat");
                if (StringUtils.isNotBlank(categoryName)) {
                    categoryNameList.add(categoryName);
                }
            }
        }

        return categoryNameList;
    }
}
