package com.netease.music.controller;

import com.netease.music.common.log.LogConstant;
import com.netease.music.service.ProxyIpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description 代理
 * @Author lilong
 * @Date 2019-07-19 19:19
 */
@Controller
@RequestMapping("proxy")
public class ProxyController extends BaseController {
    @Autowired
    private ProxyIpService proxyIpService;

    @RequestMapping("inputProxyManually")
    public String inputProxyIp() {
        return "inputProxyManually";
    }

    @RequestMapping("updateProxyManually")
    @ResponseBody
    public String updateProxyManually(String proxyIps) {
        proxyIpService.updateProxyManually(proxyIps);
        return "send success!";
    }

    @RequestMapping("addProxyIp")
    @ResponseBody
    public String addProxyIp() {
        proxyIpService.addProxyIp();
        return "send success!";
    }

    @RequestMapping(value = "refreshProxyIp", method = RequestMethod.GET)
    @ResponseBody
    public String refreshProxyIp() {
        try {
            proxyIpService.refreshProxyIp();
            return "success";
        } catch (Exception e) {
            LogConstant.BUS.error("refreshProxyIp failed: {}", e.getMessage(), e);
            return "fail";
        }
    }
}
