package com.netease.music.dao.po;

import com.netease.music.entity.enums.SexEnum;
import java.io.Serializable;
import java.util.Date;

public class UserInfoPO implements Serializable {
    private Long id;

    private Long userId;

    private String userName;

    private SexEnum sex;

    private Byte age;

    private String ageDescription;

    private String area;

    private String personalDescription;

    private Integer totalListen;

    private Integer followeeCount;

    private Integer followerCount;

    private Byte vipLevel;

    private Date dbUpdateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public SexEnum getSex() {
        return sex;
    }

    public void setSex(SexEnum sex) {
        this.sex = sex;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    public String getAgeDescription() {
        return ageDescription;
    }

    public void setAgeDescription(String ageDescription) {
        this.ageDescription = ageDescription == null ? null : ageDescription.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getPersonalDescription() {
        return personalDescription;
    }

    public void setPersonalDescription(String personalDescription) {
        this.personalDescription = personalDescription == null ? null : personalDescription.trim();
    }

    public Integer getTotalListen() {
        return totalListen;
    }

    public void setTotalListen(Integer totalListen) {
        this.totalListen = totalListen;
    }

    public Integer getFolloweeCount() {
        return followeeCount;
    }

    public void setFolloweeCount(Integer followeeCount) {
        this.followeeCount = followeeCount;
    }

    public Integer getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(Integer followerCount) {
        this.followerCount = followerCount;
    }

    public Byte getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(Byte vipLevel) {
        this.vipLevel = vipLevel;
    }

    public Date getDbUpdateTime() {
        return dbUpdateTime;
    }

    public void setDbUpdateTime(Date dbUpdateTime) {
        this.dbUpdateTime = dbUpdateTime;
    }
}