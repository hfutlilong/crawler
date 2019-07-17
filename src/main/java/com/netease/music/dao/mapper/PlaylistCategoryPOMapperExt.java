package com.netease.music.dao.mapper;


import com.netease.music.dao.po.PlaylistCategoryPO;

import java.util.List;

public interface PlaylistCategoryPOMapperExt extends PlaylistCategoryPOMapper {
    void batchInsert(List<PlaylistCategoryPO> list);
}