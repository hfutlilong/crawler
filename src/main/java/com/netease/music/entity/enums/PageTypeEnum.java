package com.netease.music.entity.enums;

public enum PageTypeEnum {
    PLAY_LISTS(0, "歌单列表页"),
    PLAY_LIST(1, "歌单歌曲列表页"),
    SONG(2, "歌曲页");

    private int intValue;
    private String desc;

    PageTypeEnum(int intValue, String desc) {
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

    public static PageTypeEnum valueOf(Integer intValue) {
        if (null == intValue) {
            return null;
        }
        for (PageTypeEnum pageTypeEnum : values()) {
            if (pageTypeEnum.getIntValue() == intValue) {
                return pageTypeEnum;
            }
        }
        return null;
    }
}
