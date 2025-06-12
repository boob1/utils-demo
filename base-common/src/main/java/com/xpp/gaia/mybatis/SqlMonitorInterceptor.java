package com.xpp.gaia.mybatis;

import com.xpp.gaia.toolkit.ActionResult;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;

/**
 * SQL处理监视拦截器
 *
 * @author Akira
 * @since 2022/3/12
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
@Slf4j
public class SqlMonitorInterceptor implements Interceptor {

    MybatisMonitorProperties monitorProperties;

    public void setSlowSqlMonitorProperties(MybatisMonitorProperties monitorProperties) {
        this.monitorProperties = monitorProperties;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MybatisInvocationWrapper invocationWrapper = new MybatisInvocationWrapper().buildWrapper(invocation);
        String sqlId = invocationWrapper.getMappedStatement().getId();
        long start = System.currentTimeMillis();
        Object returnValue = null;
        Exception ex = null;
        try {
            returnValue = invocation.proceed();
        } catch (InvocationTargetException ite) {
            ex = ite;
            throw new MybatisProcessExcetion(ActionResult.CODE_FAILED, ite.getTargetException().toString());
        } catch (Exception e) {
            ex = e;
            throw new MybatisProcessExcetion(ActionResult.CODE_FAILED, "数据库脚本执行异常:" + e.getMessage());
        } finally {
            long end = System.currentTimeMillis();
            long costed = (end - start);
            if (ex != null) {
                log.error("{} sql异常: ", sqlId, ex.getCause());
                log.error("==>  {}", showSql(invocationWrapper));
            } else {
                if (costed >= this.monitorProperties.getSlowsqlLimit()) {
                    log.warn("{} 慢Sql: {}", sqlId, showSql(invocationWrapper));
                    log.warn("==>  Sql Cost: {}(ms)", costed);
                } else {
                    log.info("==>  Sql Cost: {}(ms)", costed);
                }
            }
        }
        return returnValue;
    }

    @Override
    public Object plugin(Object target) {
        return Interceptor.super.plugin(target);
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }

    protected String showSql(MybatisInvocationWrapper invocationWrapper) {
        BoundSql boundSql = invocationWrapper.getBoundSql();
        Configuration configuration = invocationWrapper.getMappedStatement().getConfiguration();
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (parameterMappings.size() > 0 && parameterObject != null) {
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
                sql = sql.replaceFirst("\\?", getParameterValue(parameterObject));

            } else {
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings) {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName)) {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    } else if (boundSql.hasAdditionalParameter(propertyName)) {
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?", getParameterValue(obj));
                    }
                }
            }
        }
        return sql;
    }

    private String getParameterValue(Object obj) {
        String value = null;
        if (obj instanceof String) {
            value = "'" + obj.toString() + "'";
            value = value.replaceAll("\\\\", "\\\\\\\\");
            value = value.replaceAll("\\$", "\\\\\\$");
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(obj) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "";
            }
        }
        return value;
    }
}
