package com.netease.music.entity.enums;

public enum CrawlingStatusEnum {
    UN_CRAWLERED(0, "未爬取"),
    CRAWLERED(1, "已爬取"),
    CRAWLING_FAILED(2, "爬取失败");

    private int intValue;
    private String desc;

    CrawlingStatusEnum(int intValue, String desc) {
        this.intValue = intValue;
        this.desc = desc;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static CrawlingStatusEnum valueOf(Integer intValue) {
        if (null == intValue) {
            return null;
        }
        for (CrawlingStatusEnum crawlingStatusEnum : values()) {
            if (crawlingStatusEnum.getIntValue() == intValue) {
                return crawlingStatusEnum;
            }
        }
        return null;
    }
}
