package com.netease.music.dao.mapper;

import com.netease.music.dao.po.UserAgentPO;
import com.netease.music.dao.po.UserAgentPOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserAgentPOMapper {
    int countByExample(UserAgentPOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserAgentPO record);

    int insertSelective(UserAgentPO record);

    List<UserAgentPO> selectByExample(UserAgentPOExample example);

    UserAgentPO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserAgentPO record, @Param("example") UserAgentPOExample example);

    int updateByExample(@Param("record") UserAgentPO record, @Param("example") UserAgentPOExample example);

    int updateByPrimaryKeySelective(UserAgentPO record);

    int updateByPrimaryKey(UserAgentPO record);
}