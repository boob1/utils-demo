package com.xpp.gaia.auth.sqlparse;

import com.xpp.gaia.auth.DataScope;

/**
 * 权限Sql工具类
 *
 * @author Akira
 * @since 2022/5/17
 */
public class AuthSqlHelper {
    /**
     * 将集合转化为的in语句
     *
     * @param dataScope
     * @return
     */
    public static String getForEachIn(DataScope dataScope, Object[] args) {
        String prefixAndSuffix = "";
        if (dataScope != DataScope.ORG) {
            prefixAndSuffix = "'";
        }
        StringBuilder sb = new StringBuilder("(");
        for (int i = 0; i < args.length; i++) {
            sb.append(prefixAndSuffix + args[i] + prefixAndSuffix);
            if (i < args.length - 1) {
                sb.append(",");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * 将集合转化为union语句
     *
     * @param dataScope
     * @return
     */
    public static String getForUnion(DataScope dataScope, Object[] args) {
        String prefixAndSuffix = "";
        StringBuilder sb = new StringBuilder("(");
        if (dataScope == DataScope.ORG) {
            for (int i = 0; i < args.length; i++) {
                sb.append("select " + args[i] + " from dual");
                if (i < args.length - 1) {
                    sb.append(" union ");
                }
            }
        } else {
            for (int i = 0; i < args.length; i++) {
                sb.append("select '" + args[i] + "' from dual");
                if (i < args.length - 1) {
                    sb.append(" union ");
                }
            }
        }
        sb.append(")");
        return sb.toString();
    }

}
