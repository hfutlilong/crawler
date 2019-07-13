package com.netease.music.dao.mapper;

import com.netease.music.dao.po.SongPO;
import com.netease.music.dao.po.SongPOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SongPOMapper {
    int countByExample(SongPOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SongPO record);

    int insertSelective(SongPO record);

    List<SongPO> selectByExample(SongPOExample example);

    SongPO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SongPO record, @Param("example") SongPOExample example);

    int updateByExample(@Param("record") SongPO record, @Param("example") SongPOExample example);

    int updateByPrimaryKeySelective(SongPO record);

    int updateByPrimaryKey(SongPO record);
}