package com.netease.music.common.exception;

import com.netease.music.common.error.CrawlerErrorCode;

import java.util.HashMap;
import java.util.Map;

public class CrawlerException extends RuntimeException {
    /**
     * 错误码
     */
    private int errorCode;
    /**
     * 发生错误的数据信息
     */
    private Map<String,Object> attachInfoMap;

    public CrawlerException(int errorCode,String message,Throwable cause){
        super(message,cause);
        this.errorCode = errorCode;
    }

    public CrawlerException(int errorCode,String message){
        super(message);
        this.errorCode = errorCode;
    }
    public CrawlerException(String message){
        this(CrawlerErrorCode.BIZ_ERROR,message);
    }

    public CrawlerException(String message,Throwable cause){
        this(CrawlerErrorCode.BIZ_ERROR,message,cause);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public CrawlerException setErrorCode(int errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public CrawlerException addAttachInfo(String key,Object value){
        if(null == attachInfoMap){
            attachInfoMap = new HashMap<>();
        }
        attachInfoMap.put(key,value);
        return this;
    }

    public Object getAttachInfo(String key){
        return attachInfoMap.get(key);
    }
}