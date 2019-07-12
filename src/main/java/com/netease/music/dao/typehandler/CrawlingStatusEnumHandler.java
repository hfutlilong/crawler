package com.netease.music.dao.typehandler;

import com.netease.music.entity.enums.CrawlingStatusEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CrawlingStatusEnumHandler extends BaseTypeHandler<CrawlingStatusEnum> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, CrawlingStatusEnum crawlingStatusEnum,
                                    JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, crawlingStatusEnum.getIntValue());
    }

    @Override
    public CrawlingStatusEnum getNullableResult(ResultSet resultSet, String s) throws SQLException {
        int intValue = resultSet.getInt(s);
        return resultSet.wasNull() ? null : CrawlingStatusEnum.valueOf(intValue);
    }

    @Override
    public CrawlingStatusEnum getNullableResult(ResultSet resultSet, int i) throws SQLException {
        int intValue = resultSet.getInt(i);
        return resultSet.wasNull() ? null : CrawlingStatusEnum.valueOf(intValue);
    }

    @Override
    public CrawlingStatusEnum getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int intValue = callableStatement.getInt(i);
        return callableStatement.wasNull() ? null : CrawlingStatusEnum.valueOf(intValue);
    }
}
