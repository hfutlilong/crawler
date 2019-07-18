package com.netease.music.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Description 页面跳转
 * @Author lilong
 * @Date 2019-07-18 17:08
 */
@Controller
@RequestMapping("page")
public class PageController extends BaseController {
    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public String hello() {
        return "hello";
    }
}
