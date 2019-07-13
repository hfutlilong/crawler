package com.netease.music.dao.mapper;

import com.netease.music.dao.po.PlayListSongRelationPO;
import com.netease.music.dao.po.PlayListSongRelationPOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlayListSongRelationPOMapper {
    int countByExample(PlayListSongRelationPOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PlayListSongRelationPO record);

    int insertSelective(PlayListSongRelationPO record);

    List<PlayListSongRelationPO> selectByExample(PlayListSongRelationPOExample example);

    PlayListSongRelationPO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PlayListSongRelationPO record, @Param("example") PlayListSongRelationPOExample example);

    int updateByExample(@Param("record") PlayListSongRelationPO record, @Param("example") PlayListSongRelationPOExample example);

    int updateByPrimaryKeySelective(PlayListSongRelationPO record);

    int updateByPrimaryKey(PlayListSongRelationPO record);
}