package com.netease.music.dao.mapper;

import com.netease.music.dao.po.MusicCommentPO;
import com.netease.music.dao.po.MusicCommentPOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MusicCommentPOMapper {
    long countByExample(MusicCommentPOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MusicCommentPO record);

    int insertSelective(MusicCommentPO record);

    List<MusicCommentPO> selectByExampleWithBLOBs(MusicCommentPOExample example);

    List<MusicCommentPO> selectByExample(MusicCommentPOExample example);

    MusicCommentPO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MusicCommentPO record, @Param("example") MusicCommentPOExample example);

    int updateByExampleWithBLOBs(@Param("record") MusicCommentPO record, @Param("example") MusicCommentPOExample example);

    int updateByExample(@Param("record") MusicCommentPO record, @Param("example") MusicCommentPOExample example);

    int updateByPrimaryKeySelective(MusicCommentPO record);

    int updateByPrimaryKeyWithBLOBs(MusicCommentPO record);

    int updateByPrimaryKey(MusicCommentPO record);
}