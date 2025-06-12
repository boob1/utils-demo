package com.xpp.gaia.mybatis;

import com.xpp.gaia.auth.DataScope;
import com.xpp.gaia.mybatis.permission.PermitBean;
import com.xpp.gaia.mybatis.permission.PermitMethod;
import com.xpp.gaia.mybatis.permission.PermitProceedInject;
import java.util.Properties;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

/**
 * Mybatis数据权限查询拦截器
 *
 * @author Akira
 * @apiNote https://github.com/pagehelper/Mybatis-PageHelper/blob/master/wikis/zh/Interceptor.md
 * @since 2022/2/14
 */
@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,
                RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
})
@Slf4j
public class QueryPermitInterceptor implements Interceptor {

    private static final ThreadLocal<PermitBean> dataScopeHolder = new ThreadLocal<PermitBean>();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        try {
            MybatisInvocationWrapper invocationWrapper = new MybatisInvocationWrapper().buildWrapper(invocation);
            PermitBean permitBean = dataScopeHolder.get();
            if (permitBean == null) {
                return invocation.proceed();
            }
            // 需要提前删除，否则其他sql执行时仍会获取到
            dataScopeHolder.remove();
            return permitBean.getPermitProceedInject().inject(invocationWrapper, permitBean);
        } finally {
            dataScopeHolder.remove();
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        Interceptor.super.setProperties(properties);
    }

    /**
     * 数据权限控制
     *
     * @param permitMethod
     * @param permitProceedInject
     * @param dataScope
     * @param dialect
     * @param methodArgs
     */
    public static void permit(@NotNull PermitMethod permitMethod,
                              @NotNull PermitProceedInject permitProceedInject,
                              DataScope dataScope,
                              String dialect,
                              Object[] methodArgs) {
        PermitBean permitBean = permitMethod.permit(dataScope, dialect, methodArgs);
        if (permitBean == null) {
            return;
        }
        if (permitProceedInject == null) {
            throw new MybatisProcessExcetion("未定义权限处理方式");
        }
        permitBean.setPermitProceedInject(permitProceedInject);
        dataScopeHolder.set(permitBean);
    }

}