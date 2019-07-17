package com.netease.music.dao.mapper;


import com.netease.music.dao.po.ProxyPO;

public interface ProxyPOMapperExt extends ProxyPOMapper {
    int insertOnDuplicateUpdate(ProxyPO proxyPO);
}