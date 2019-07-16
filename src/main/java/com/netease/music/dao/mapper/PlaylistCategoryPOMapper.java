package com.netease.music.dao.mapper;

import com.netease.music.dao.po.PlaylistCategoryPO;
import com.netease.music.dao.po.PlaylistCategoryPOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlaylistCategoryPOMapper {
    int countByExample(PlaylistCategoryPOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PlaylistCategoryPO record);

    int insertSelective(PlaylistCategoryPO record);

    List<PlaylistCategoryPO> selectByExample(PlaylistCategoryPOExample example);

    PlaylistCategoryPO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PlaylistCategoryPO record, @Param("example") PlaylistCategoryPOExample example);

    int updateByExample(@Param("record") PlaylistCategoryPO record, @Param("example") PlaylistCategoryPOExample example);

    int updateByPrimaryKeySelective(PlaylistCategoryPO record);

    int updateByPrimaryKey(PlaylistCategoryPO record);
}