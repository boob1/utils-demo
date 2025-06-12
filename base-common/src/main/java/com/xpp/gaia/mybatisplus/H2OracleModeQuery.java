package com.xpp.gaia.mybatisplus;

import com.baomidou.mybatisplus.generator.config.querys.H2Query;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * H2Database Oracle-Mode 表数据查询
 *
 * @author Akira
 * @since 2021/10/28
 */
public class H2OracleModeQuery extends H2Query {

    /**
     * 重写的目的是为了数据类型重定义
     * @return
     */
    @Override
    public String tableFieldsSql() {
        return "SELECT TABLE_NAME, REMARKS, COLUMN_NAME, "
                + "CASE TYPE_NAME WHEN 'DECIMAL' then COLUMN_TYPE ELSE TYPE_NAME END as TYPE_NAME "
                + "FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME= '%s' ";
    }

    @Override
    public boolean isKeyIdentity(ResultSet results) throws SQLException {
        return false;
    }
}
