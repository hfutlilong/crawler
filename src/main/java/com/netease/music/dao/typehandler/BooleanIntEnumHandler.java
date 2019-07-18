package com.netease.music.dao.typehandler;

import com.netease.music.entity.enums.BooleanIntEnum;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BooleanIntEnumHandler extends BaseTypeHandler<BooleanIntEnum> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, BooleanIntEnum booleanIntEnum,
                                    JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, booleanIntEnum.getIntValue());
    }

    @Override
    public BooleanIntEnum getNullableResult(ResultSet resultSet, String s) throws SQLException {
        int intValue = resultSet.getInt(s);
        return resultSet.wasNull() ? null : BooleanIntEnum.valueOf(intValue);
    }

    @Override
    public BooleanIntEnum getNullableResult(ResultSet resultSet, int i) throws SQLException {
        int intValue = resultSet.getInt(i);
        return resultSet.wasNull() ? null : BooleanIntEnum.valueOf(intValue);
    }

    @Override
    public BooleanIntEnum getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        int intValue = callableStatement.getInt(i);
        return callableStatement.wasNull() ? null : BooleanIntEnum.valueOf(intValue);
    }
}
