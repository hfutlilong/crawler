package com.netease.music.dao.mapper;

import com.netease.music.dao.po.MusicPagePO;
import com.netease.music.dao.po.MusicPagePOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MusicPagePOMapper {
    long countByExample(MusicPagePOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MusicPagePO record);

    int insertSelective(MusicPagePO record);

    List<MusicPagePO> selectByExample(MusicPagePOExample example);

    MusicPagePO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MusicPagePO record, @Param("example") MusicPagePOExample example);

    int updateByExample(@Param("record") MusicPagePO record, @Param("example") MusicPagePOExample example);

    int updateByPrimaryKeySelective(MusicPagePO record);

    int updateByPrimaryKey(MusicPagePO record);
}