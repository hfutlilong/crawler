package com.netease.music.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description 测试
 * @Author lilong
 * @Date 2019-07-12 13:57
 */
@Controller
public class Test {
    @RequestMapping(value ="/hello", method = RequestMethod.GET)
    @ResponseBody
    public String testHello() {
        return "10086";
    }
}
