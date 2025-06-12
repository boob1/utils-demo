package com.xpp.gaia.mybatis.permission;

import com.xpp.gaia.mybatis.MybatisInvocationWrapper;

/**
 * 数据权限注入器
 *
 * @author Akira
 * @since 2022/3/20
 */
public interface PermitProceedInject {

    Object inject(MybatisInvocationWrapper invocationWrapper, PermitBean permitBean) throws Throwable;
}
