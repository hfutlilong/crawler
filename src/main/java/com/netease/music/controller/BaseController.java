//package com.netease.music.controller;
//
//import com.netease.music.common.TimestampEditor;
//import com.netease.music.entity.constant.LogConstant;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.WebDataBinder;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.InitBinder;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import javax.servlet.http.HttpServletResponse;
//import java.sql.Timestamp;
//import java.util.Date;
//
//@Controller
//public class BaseController {
//    private static final String CLIENT_ABORT_EXCEPTION = "org.apache.catalina.connector.ClientAbortException";
//
//    @ExceptionHandler(Throwable.class)
//    public String handleException(Throwable e, HttpServletResponse response) {
//        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
//        response.setHeader("Expires", "Sat, 26 Jul 1997 05:00:00 GMT");
//        response.setHeader("Pragma", "No-cache");
//
//        if (null != e) {
//            LogConstant.BUS.info("BaseController exception {}", e.getMessage(), e);
//        }
//        return "index";
//    }
//
//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//        binder.setAutoGrowCollectionLimit(Integer.MAX_VALUE);
//        binder.registerCustomEditor(Timestamp.class, new TimestampEditor());
//        binder.registerCustomEditor(Date.class, new TimestampEditor());
//    }
//
//    /**
//     * 重定向
//     * @param redirectUrl
//     * @param attributes
//     * @param key
//     * @param value
//     * @return
//     */
//    protected String redirect(String redirectUrl, RedirectAttributes attributes, String key, Object value) {
//        if(redirectUrl == null) {
//            return null;
//        } else if(attributes != null && key != null && value != null) {
//            attributes.addAttribute(key, value);
//            return "redirect:" + redirectUrl;
//        } else {
//            return "redirect:" + redirectUrl;
//        }
//    }
//}
