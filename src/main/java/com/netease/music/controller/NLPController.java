package com.netease.music.controller;

import com.netease.music.service.NLPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("nlp")
public class NLPController extends BaseController {
    @Autowired
    private NLPService nlpService;

    @RequestMapping("test")
    @ResponseBody
    public String test() {
        nlpService.testHanNlp();
        return "nlpService.testHanNlp();";
    }
}
