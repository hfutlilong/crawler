package com.netease.music.dao.mapper;

import com.netease.music.dao.po.PlayListPO;
import com.netease.music.dao.po.PlayListPOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PlayListPOMapper {
    int countByExample(PlayListPOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PlayListPO record);

    int insertSelective(PlayListPO record);

    List<PlayListPO> selectByExample(PlayListPOExample example);

    PlayListPO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PlayListPO record, @Param("example") PlayListPOExample example);

    int updateByExample(@Param("record") PlayListPO record, @Param("example") PlayListPOExample example);

    int updateByPrimaryKeySelective(PlayListPO record);

    int updateByPrimaryKey(PlayListPO record);
}