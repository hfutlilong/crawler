package com.netease.music.dao.mapper;


import com.netease.music.dao.po.UserInfoPO;

public interface UserInfoPOMapperExt extends UserInfoPOMapper {
    int insertOnDuplicateUpdate(UserInfoPO userInfoPO);
}