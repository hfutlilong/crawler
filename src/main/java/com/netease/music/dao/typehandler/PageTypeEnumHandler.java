package com.netease.music.dao.typehandler;

import com.netease.music.entity.enums.PageTypeEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PageTypeEnumHandler extends BaseTypeHandler<PageTypeEnum> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, PageTypeEnum pageTypeEnum,
                                    JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, pageTypeEnum.getIntValue());
    }

    @Override
    public PageTypeEnum getNullableResult(ResultSet resultSet, String s) throws SQLException {
        int intValue = resultSet.getInt(s);
        return resultSet.wasNull() ? null : PageTypeEnum.valueOf(intValue);
    }

    @Override
    public PageTypeEnum getNullableResult(ResultSet resultSet, int i) throws SQLException {
        int intValue = resultSet.getInt(i);
        return resultSet.wasNull() ? null : PageTypeEnum.valueOf(intValue);
    }

    @Override
    public PageTypeEnum getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int intValue = callableStatement.getInt(i);
        return callableStatement.wasNull() ? null : PageTypeEnum.valueOf(intValue);
    }
}
