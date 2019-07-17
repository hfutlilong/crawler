package com.netease.music.dao.mapper;

import com.netease.music.dao.po.ProxyPO;
import com.netease.music.dao.po.ProxyPOExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProxyPOMapper {
    int countByExample(ProxyPOExample example);

    int deleteByExample(ProxyPOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProxyPO record);

    int insertSelective(ProxyPO record);

    List<ProxyPO> selectByExample(ProxyPOExample example);

    ProxyPO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProxyPO record, @Param("example") ProxyPOExample example);

    int updateByExample(@Param("record") ProxyPO record, @Param("example") ProxyPOExample example);

    int updateByPrimaryKeySelective(ProxyPO record);

    int updateByPrimaryKey(ProxyPO record);
}