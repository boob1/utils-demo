package com.xpp.gaia.mybatis;

import static com.xpp.gaia.boot.utils.SqlHelper.UPDATE_KEY;
import static com.xpp.gaia.mybatis.RuleColumnEnum.UPDATER;
import static com.xpp.gaia.mybatis.RuleColumnEnum.UPDATER_ORG;
import static com.xpp.gaia.mybatis.RuleColumnEnum.UPDATE_TIME;

import com.xpp.gaia.auth.Auth;
import com.xpp.gaia.auth.AuthUser;
import com.xpp.gaia.boot.utils.SqlHelper;
import com.xpp.gaia.toolkit.ActionResult;
import com.xpp.gaia.toolkit.action.ActionHandler;
import com.xpp.gaia.toolkit.action.ActionVerifyException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.update.UpdateSet;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

/**
 * Mybatis特殊字段注入拦截器
 *
 * @author Akira
 * @apiNote https://blog.csdn.net/qq_38225558/article/details/85018810
 * @since 2022/2/14
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
})
@Slf4j
public class UpdateInjectInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MybatisInvocationWrapper wrapper = new MybatisInvocationWrapper().buildWrapper(invocation);
        if (wrapper.getStatement() == null || !(wrapper.getStatement() instanceof Update)) {
            return invocation.proceed();
        }
        Update updateStatement = (Update) wrapper.getStatement();
        Table table = updateStatement.getTable();
        if (table == null) {
            throw new ActionVerifyException(ActionResult.CODE_FAILED, "update语句中缺少表名");
        }
        Connection connection = wrapper.getExecutor().getTransaction().getConnection();
        String tableName = table.getName();
        // 表中不包含此update_time字段则直接跳过
        if (!SqlHelper.containsUpdatePackInTable(connection, tableName, UPDATE_KEY)) {
            return invocation.proceed();
        }

        // 解析sql中是否已经set此字段
        ArrayList<UpdateSet> updateSets = updateStatement.getUpdateSets();
        if (updateSets.isEmpty()) {
            throw new ActionVerifyException(ActionResult.CODE_FAILED, "update语句中缺少set表达式");
        }
        if (!SqlHelper.containsInExpression(updateSets, UPDATE_TIME.name())) {
            UpdateSet updateSet = new UpdateSet(new Column(UPDATE_TIME.name()),
                    CCJSqlParserUtil.parseExpression(SqlHelper.getUpdateColumnDefault(connection)));
            updateSets.add(updateSet);
        }
        AuthUser user = Auth.getUser();
        if (user != null) {
            if (!SqlHelper.containsInExpression(updateSets, UPDATER.name())
                    && SqlHelper.containsUpdatePackInTable(connection, tableName, UPDATER.name())) {
                UpdateSet updateSet = new UpdateSet(new Column(UPDATER.name()),
                        CCJSqlParserUtil.parseExpression("'" + user.getAccount() + "'"));
                updateSets.add(updateSet);
            }
            if (!SqlHelper.containsInExpression(updateSets, UPDATER_ORG.name())
                    && SqlHelper.containsUpdatePackInTable(connection, tableName, UPDATER_ORG.name())) {
                UpdateSet updateSet = new UpdateSet(new Column(UPDATER_ORG.name()),
                        CCJSqlParserUtil.parseExpression("" + user.getOrgId()));
                updateSets.add(updateSet);
            }
        }
        String newSql = updateStatement.toString();
        wrapper.rebuildMappedStatement(newSql);
        return wrapper.execute();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // String driver = properties.getProperty("spring.datasource.driverClassName");
        // this.dbTyper = DbTyper.analyseByStr(driver);
        Interceptor.super.setProperties(properties);
    }

    private AuthUser checkAndGetUser() {
        AuthUser user = Auth.getUser();
        ActionHandler.assertCheckNull(user, "缺少用户信息");
        return user;
    }

}
