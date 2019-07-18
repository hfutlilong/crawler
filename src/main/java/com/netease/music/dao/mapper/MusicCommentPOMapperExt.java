package com.netease.music.dao.mapper;


import com.netease.music.dao.po.MusicCommentPO;

public interface MusicCommentPOMapperExt extends MusicCommentPOMapper {
    int insertOnDuplicateUpdate(MusicCommentPO musicCommentPO);
}