package com.netease.music.common;

import com.netease.music.entity.constant.LogConstant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.propertyeditors.PropertiesEditor;

import java.sql.Timestamp;

public class TimestampEditor extends PropertiesEditor {
    public TimestampEditor() {
    }

    public void setAsText(String text) throws IllegalArgumentException {
        if (StringUtils.isEmpty(text) || "0".equals(text)) {
            this.setValue(null);
            return;
        }
        try{
            Timestamp t = new Timestamp(Long.valueOf(text));
            this.setValue(t);
            return;
        }catch (Exception e){
            LogConstant.BUS.error("setAsText error, text={}", text, e);
        }
        Timestamp t = Timestamp.valueOf(text);
        this.setValue(t);
    }

    public String getAsText() {
        return this.getValue().toString();
    }
}
