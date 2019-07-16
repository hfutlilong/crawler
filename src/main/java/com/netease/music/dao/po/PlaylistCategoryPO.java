package com.netease.music.dao.po;

import com.netease.music.entity.enums.CrawlingStatusEnum;
import java.io.Serializable;
import java.util.Date;

public class PlaylistCategoryPO implements Serializable {
    private Long id;

    private String categoryName;

    private CrawlingStatusEnum crawlingStatus;

    private Date dbUpdateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName == null ? null : categoryName.trim();
    }

    public CrawlingStatusEnum getCrawlingStatus() {
        return crawlingStatus;
    }

    public void setCrawlingStatus(CrawlingStatusEnum crawlingStatus) {
        this.crawlingStatus = crawlingStatus;
    }

    public Date getDbUpdateTime() {
        return dbUpdateTime;
    }

    public void setDbUpdateTime(Date dbUpdateTime) {
        this.dbUpdateTime = dbUpdateTime;
    }
}