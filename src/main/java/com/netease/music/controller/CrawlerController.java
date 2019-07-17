package com.netease.music.controller;

import com.netease.music.common.log.LogConstant;
import com.netease.music.service.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("crawler")
public class CrawlerController extends BaseController {
    @Autowired
    private CrawlerService crawlerService;

    @RequestMapping(value = "autoCrawling", method = RequestMethod.GET)
    @ResponseBody
    public String autoCrawling() {
        try {
            crawlerService.autoCrawling();
        } catch (Exception e) {
            LogConstant.BUS.error("autoCrawling failed: {}", e.getMessage(), e);
            return e.getMessage();
        }
        return "success";
    }

    @RequestMapping(value = "crawlingOnePlayList", method = RequestMethod.GET)
    @ResponseBody
    public String crawlingOnePlayList(Long playListId) {
        try {
            crawlerService.crawlingOnePlayList(playListId);
        } catch (Exception e) {
            LogConstant.BUS.error("autoCrawling failed: {}", e.getMessage(), e);
            return e.getMessage();
        }
        return "success";
    }

    @RequestMapping(value = "crawlingSongInfo", method = RequestMethod.GET)
    @ResponseBody
    public String crawlingSong() {
            new Thread(() -> {
                try {
                    crawlerService.crawlingSongInfo();
                } catch (Exception e) {
                    LogConstant.BUS.error("crawlingSongInfo failed: {}", e.getMessage(), e);
                }
            }).start();

        return "success";
    }


    @RequestMapping(value = "crawlingPlayListComment", method = RequestMethod.GET)
    @ResponseBody
    public String crawlingPlayListComment(Long playListId) {
        new Thread(() -> {
            try {
                crawlerService.doCrawlingPlayListComment(playListId);
            } catch (Exception e) {
                LogConstant.BUS.error("crawlingSongInfo failed: {}", e.getMessage(), e);
            }
        }).start();

        return "success";
    }

    @RequestMapping("hello")
    @ResponseBody
    public String testHello() {
        LogConstant.BUS.debug("DEBUG LOG TEST 测试");
        LogConstant.BUS.info("INFO LOG TEST 测试");
        LogConstant.BUS.error("ERROR LOG TEST 测试");

        return "66666666666";
    }
}
