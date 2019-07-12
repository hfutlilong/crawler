package com.netease.music.entity.enums;

public enum SexEnum {
    MALE(0, "男"),
    FEMALE(1, "女");

    private int intValue;
    private String desc;

    SexEnum(int intValue, String desc) {
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

    public static SexEnum valueOf(Integer intValue) {
        if (null == intValue) {
            return null;
        }
        for (SexEnum sexEnum : values()) {
            if (sexEnum.getIntValue() == intValue) {
                return sexEnum;
            }
        }
        return null;
    }
}
