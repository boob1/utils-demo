package com.xpp.gaia.mybatisplus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import java.util.Collections;
import java.util.Map;

/**
 * 修正MP H2数据库识别的问题
 *
 * @author Akira
 * @since 2021/10/29
 */
public class MpDataSourceConfigRoute {

    /**
     * 扩展并修正
     *
     * @param url
     * @param userName
     * @param password
     * @return Mp DataSourceConfig.Builder
     */
    public static DataSourceConfig.Builder route(String url, String userName, String password) {
        DataSourceConfig.Builder builder = new DataSourceConfig.Builder(url, userName, password);
        String jdbcPath = url.toLowerCase();
        if (jdbcPath.contains(":h2:") && jdbcPath.contains("MODE=Oracle".toLowerCase())) {
            return builder
                    .dbQuery(new H2OracleModeQuery())
                    .typeConvert(new H2OracleModeTypeConvert());
        }
        // 其他交给mp自行识别
        return builder;
    }

    /**
     * 获取Entity的IdType类型
     *
     * @param dataSourceConfig
     * @return
     */
    public static IdType idType(DataSourceConfig dataSourceConfig) {
        String jdbcPath = dataSourceConfig.getUrl().toLowerCase();
        if (jdbcPath.contains(":h2:") && jdbcPath.contains("MODE=Oracle".toLowerCase())) {
            return IdType.INPUT;
        }
        if (dataSourceConfig.getDbType() == DbType.ORACLE) {
            return IdType.INPUT;
        }
        return IdType.AUTO;
    }

    /**
     * 获取Entity的IdType类型
     *
     * @param dataSourceConfig
     * @return
     */
    public static Map<String, Object> customMap(DataSourceConfig dataSourceConfig) {
        String jdbcPath = dataSourceConfig.getUrl().toLowerCase();
        if (jdbcPath.contains(":h2:") && jdbcPath.contains("MODE=Oracle".toLowerCase())) {
            return Collections.singletonMap("sequence", "true");
        }
        if (dataSourceConfig.getDbType() == DbType.ORACLE) {
            return Collections.singletonMap("sequence", "true");
        }
        return null;
    }
}
