package com.netease.music.controller;

import com.netease.music.common.CommonResponse;
import com.netease.music.common.exception.CrawlerException;
import com.netease.music.common.log.LogConstant;
import com.netease.music.service.NLPService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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

    @RequestMapping("inputSentence")
    public String inputSentence() {
        return "inputSentence";
    }

    @RequestMapping("getKeyworks")
    @ResponseBody
    public CommonResponse getKeyworks(String sentence) {
        try {
            List<String> keywords = nlpService.getKeywords(sentence);
            if (CollectionUtils.isEmpty(keywords)) {
                return CommonResponse.succ();
            }
            String keywordsStr = StringUtils.join(keywords, ",");
            return CommonResponse.succ(keywordsStr);
        } catch (CrawlerException e) {
            return CommonResponse.fail(e.getMessage());
        } catch (Exception e) {
            LogConstant.BUS.error("getKeyworks failed:", e);
            return CommonResponse.systemError();
        }
    }
}
