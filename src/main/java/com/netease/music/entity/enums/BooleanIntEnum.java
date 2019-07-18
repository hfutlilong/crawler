package com.netease.music.entity.enums;

public enum BooleanIntEnum {
    FALSE(0, "否"),
    TRUE(1, "是");

    private int intValue;

    private String desc;

    BooleanIntEnum(int intValue, String desc) {
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

    public static BooleanIntEnum valueOf(Integer intValue) {
        if (null == intValue) {
            return null;
        }
        for (BooleanIntEnum booleanIntEnum : values()) {
            if (booleanIntEnum.getIntValue() == intValue) {
                return booleanIntEnum;
            }
        }
        return null;
    }
}
