package com.netease.music.dao.mapper;


import com.netease.music.dao.po.UserAgentPO;

public interface UserAgentPOMapperExt extends UserAgentPOMapper {
    UserAgentPO selectRandom();
}