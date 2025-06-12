package com.xpp.gaia.mybatis.permission;

import com.github.pagehelper.PageHelper;
import com.xpp.gaia.auth.sqlparse.AuthSqlHelper;
import com.xpp.gaia.boot.utils.SqlHelper;
import com.xpp.gaia.mybatis.MybatisInvocationWrapper;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;

/**
 * 默认权限注入器
 *
 * @author Akira
 * @since 2022/3/20
 */
@Slf4j
public class DefaultPermitProceedInject implements PermitProceedInject {

    @Override
    public Object inject(MybatisInvocationWrapper invocationWrapper, PermitBean permitBean) throws Throwable {
        if (permitBean.getPager() != null) {
            PageHelper.startPage(permitBean.getPager().getPageNum(), permitBean.getPager().getPageSize());
        }
        if (permitBean.getArgs() == null || permitBean.getArgs().length == 0) {
            return invocationWrapper.execute();
        }
        String foreach = "";
        if (permitBean.getArgs().length > 1000) {
            foreach = AuthSqlHelper.getForUnion(permitBean.getDataScope(), permitBean.getArgs());
        } else {
            foreach = AuthSqlHelper.getForEachIn(permitBean.getDataScope(), permitBean.getArgs());
        }
        String suffixSql = this.processOr(permitBean.getDialect(), foreach);
        String originSql = invocationWrapper.getBoundSql().getSql();
        String newSql = "select * from (" + originSql + ") where " + suffixSql;
        invocationWrapper.rebuildMappedStatement(newSql);
        return invocationWrapper.execute();
    }

    protected String processOr(String dialect, String foreach) {
        String suffix = " in " + foreach;
        StringBuilder suffixSql = new StringBuilder("");
        if (SqlHelper.matchOR(dialect)) {
            String[] tmp = dialect.toUpperCase(Locale.ROOT).split(SqlHelper.PATTERN_OR);
            for (int i = 0; i < tmp.length; i++) {
                suffixSql.append(tmp[i]);
                suffixSql.append(suffix);
                if (i < tmp.length - 1) {
                    suffixSql.append(SqlHelper.PATTERN_OR);
                }
            }
        } else {
            suffixSql.append(dialect);
            suffixSql.append(suffix);
        }
        return suffixSql.toString();
    }
}
