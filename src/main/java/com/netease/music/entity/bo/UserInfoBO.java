package com.netease.music.entity.bo;

import java.io.Serializable;
import java.util.List;

/**
 * @Description 用户信息
 * @Author lilong
 * @Date 2019-07-17 15:33
 */
public class UserInfoBO implements Serializable {
    private static final long serialVersionUID = -491631070479254346L;

    /**
     * 位置信息
     */
    private String locationInfo;
    private String liveInfo;
    private VipRightsBO vipRights;

    /**
     * 用户id
     */
    private Long userId;
    private Integer vipType;
    private Integer userType;

    /**
     * 用户名
     */
    private String nickname;
    private String remarkName;

    /**
     * 用户标签
     */
    private List<String> expertTags;

    /**
     * 头像
     */
    private String avatarUrl;
    private String experts;
    private Integer authStatus;

    public String getLocationInfo() {
        return locationInfo;
    }

    public void setLocationInfo(String locationInfo) {
        this.locationInfo = locationInfo;
    }

    public String getLiveInfo() {
        return liveInfo;
    }

    public void setLiveInfo(String liveInfo) {
        this.liveInfo = liveInfo;
    }

    public VipRightsBO getVipRights() {
        return vipRights;
    }

    public void setVipRights(VipRightsBO vipRights) {
        this.vipRights = vipRights;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getVipType() {
        return vipType;
    }

    public void setVipType(Integer vipType) {
        this.vipType = vipType;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRemarkName() {
        return remarkName;
    }

    public void setRemarkName(String remarkName) {
        this.remarkName = remarkName;
    }

    public List<String> getExpertTags() {
        return expertTags;
    }

    public void setExpertTags(List<String> expertTags) {
        this.expertTags = expertTags;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getExperts() {
        return experts;
    }

    public void setExperts(String experts) {
        this.experts = experts;
    }

    public Integer getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(Integer authStatus) {
        this.authStatus = authStatus;
    }

    public static class VipRightsBO {
        private AssociatorBO associator;
        private String musicPackage;
        private Integer redVipAnnualCount;

        public AssociatorBO getAssociator() {
            return associator;
        }

        public void setAssociator(AssociatorBO associator) {
            this.associator = associator;
        }

        public String getMusicPackage() {
            return musicPackage;
        }

        public void setMusicPackage(String musicPackage) {
            this.musicPackage = musicPackage;
        }

        public Integer getRedVipAnnualCount() {
            return redVipAnnualCount;
        }

        public void setRedVipAnnualCount(Integer redVipAnnualCount) {
            this.redVipAnnualCount = redVipAnnualCount;
        }
    }

    public static class AssociatorBO {
        Integer vipCode;
        Boolean rights;

        public Integer getVipCode() {
            return vipCode;
        }

        public void setVipCode(Integer vipCode) {
            this.vipCode = vipCode;
        }

        public Boolean getRights() {
            return rights;
        }

        public void setRights(Boolean rights) {
            this.rights = rights;
        }
    }
}
