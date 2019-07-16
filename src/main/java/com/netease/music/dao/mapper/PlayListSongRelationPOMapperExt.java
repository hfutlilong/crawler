package com.netease.music.dao.mapper;


import com.netease.music.dao.po.PlayListSongRelationPO;

import java.util.List;

public interface PlayListSongRelationPOMapperExt extends PlayListSongRelationPOMapper {
    void batchInsert(List<PlayListSongRelationPO> list);
}