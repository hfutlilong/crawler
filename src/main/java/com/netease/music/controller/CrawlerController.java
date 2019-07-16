package com.netease.music.controller;

import com.netease.music.common.log.LogConstant;
import com.netease.music.service.CrawlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("crawler")
public class CrawlerController extends BaseController {
    @Autowired
    private CrawlerService crawlerService;

    @RequestMapping("autoCrawling")
    @ResponseBody
    public String autoCrawling() {
        try {
            crawlerService.autoCrawling();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
