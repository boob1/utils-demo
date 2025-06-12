package com.xpp.gaia.boot.utils;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.xpp.gaia.toolkit.ActionResult;
import com.xpp.gaia.toolkit.action.ActionProcessException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.update.UpdateSet;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Sql工具类
 *
 * @author Akira
 * @since 2022/2/22
 */
@Slf4j
public class SqlHelper {

    @NonNull
    private static Cache<String, Object> analysedCache = Caffeine.newBuilder().expireAfterAccess(4, TimeUnit.HOURS).build();

    public static String UPDATE_KEY = "UPDATE_TIME";

    public static String PATTERN_OR = " OR ";
    public static String PATTERN_AND = " AND ";

    /**
     * 解析sql获取sql中的表名数组
     *
     * @param sql sql语句
     * @return
     */
    public static String[] getTableNamesInSQL(String sql) {
        net.sf.jsqlparser.statement.Statement statement = null;
        try {
            statement = CCJSqlParserUtil.parse(sql);
        } catch (JSQLParserException e) {
            throw new ActionProcessException(ActionResult.CODE_FAILED, "解析sql语句错误！sql:" + sql, e);
        }
        TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
        List<String> tableList = tablesNamesFinder.getTableList(statement);
        for (int i = 0; i < tableList.size(); i++) {
            String tableName = tableList.get(i);
            //获取去掉“`”的表名
            if (tableName.startsWith("`") && tableName.endsWith("`")) {
                tableList.set(i, tableName.substring(1, tableName.length() - 1));
            }
        }
        return tableList.toArray(new String[tableList.size()]);
    }

    /**
     * 解析update sql获取sql中的表名
     *
     * @param sql sql语句
     * @return
     */
    public static String getTableNameInUpdateSQL(String sql) {
        String[] tables = getTableNamesInSQL(sql);
        return tables.length > 0 ? tables[0] : null;
    }

    /**
     * 识别表中是否update相关字段
     *
     * @param connection connection
     * @param tableName  表名
     * @return
     * @apiNote 只要含有[update_*]就认为表中定义其他update_*字段，不再单独识别个别字段
     */
    public static boolean containsUpdatePackInTable(Connection connection, String tableName, String key) {
        Object inCache = analysedCache.getIfPresent(tableName.toUpperCase() + "_" + key);
        if (inCache != null) {
            log.debug("Found table update pack in cache");
            return (Boolean) inCache;
        }
        try {
            String dbType = getDbType(connection);
            String sql = getColumnInfoSqlByDiffDB(dbType);
            if (sql == null) {
                throw new ActionProcessException(String.format("框架不支持的数据库类型[%s]", dbType));
            }
            Statement sm = connection.createStatement();
            sql = String.format(sql, dbType.contains("oracle") ? tableName.toUpperCase() : tableName, key);
            log.info(sql);
            ResultSet rs = sm.executeQuery(sql);
            while (rs.next()) {
                analysedCache.put(tableName.toUpperCase() + "_" + key, true);
                return true;
            }
            analysedCache.put(tableName.toUpperCase() + "_" + key, false);
        } catch (SQLException e) {
            log.error(e.toString());
            throw new ActionProcessException(ActionResult.CODE_FAILED, e);
        } finally {
            /* connection后续还会使用，此处不关闭
            try {
                connection.close();
            } catch (SQLException e) {
                log.error(e.toString());
                throw new ActionProcessException(ActionResult.CODE_FAILED, e);
            }
             */
        }
        return false;
    }

    public static String getDbType(Connection connection) {
        try {
            String dbType = connection.getMetaData().getDatabaseProductName().toLowerCase();
            dbType = dbType.replaceAll(" ", "").toLowerCase(Locale.ROOT);
            return dbType;
        } catch (SQLException e) {
            log.error(e.toString());
            throw new ActionProcessException(ActionResult.CODE_FAILED, e);
        }
    }

    public static String getUpdateColumnDefault(Connection connection) {
        String dbType = getDbType(connection);
        if (dbType.contains("oracle")) {
            return "sysdate";
        } else if (dbType.contains("mysql")) {
            return "now()";
        } else if (dbType.contains("sqlserver")) {
            return "getdate()";
        }
        throw new ActionProcessException(ActionResult.CODE_FAILED, "不支持的数据库类型" + dbType);
    }

    public static String getColumnInfoSqlByDiffDB(String dbType) {
        dbType = dbType.replaceAll(" ", "").toLowerCase(Locale.ROOT);
        if (dbType.contains("oracle")) {
            return "SELECT * FROM ALL_TAB_COLUMNS WHERE TABLE_NAME = '%s' AND COLUMN_NAME = '%s'";
        } else if (dbType.contains("mysql")) {
            return "show full fields from `%s` where Field = '%s'";
        } else if (dbType.contains("sqlserver")) {
            return "Select * from SysColumns WHERE id = Object_Id('%s') AND name = '%s'";
        }
        throw new ActionProcessException(ActionResult.CODE_FAILED, "不支持的数据库类型" + dbType);
    }

    /**
     * 判断UpdateSet中是否存在指定字段
     *
     * @param updateSets        updateSet
     * @param updatedColumnName 指定字段
     * @return
     */
    public static boolean containsInExpression(List<UpdateSet> updateSets, String updatedColumnName) {
        for (UpdateSet updateSet : updateSets) {
            for (Column column : updateSet.getColumns()) {
                if (String.valueOf(column.getColumnName()).equalsIgnoreCase(updatedColumnName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断sql中是否存在'AND'
     *
     * @param str
     * @return
     */
    public static boolean matchAND(String str) {
        return Pattern.matches(".*" + PATTERN_AND.toLowerCase(Locale.ROOT) + ".*", str.toLowerCase(Locale.ROOT));
    }

    /**
     * 判断sql中是否存在'OR'
     *
     * @param str
     * @return
     */
    public static boolean matchOR(String str) {
        return Pattern.matches(".*" + PATTERN_OR.toLowerCase(Locale.ROOT) + ".*", str.toLowerCase(Locale.ROOT));
    }

}
