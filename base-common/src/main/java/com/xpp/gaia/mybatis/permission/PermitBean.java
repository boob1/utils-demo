package com.xpp.gaia.mybatis.permission;

import com.xpp.gaia.auth.DataScope;
import com.xpp.gaia.toolkit.Pager;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 数据权限数据Bean
 *
 * @author Akira
 * @since 2022/3/20
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PermitBean {
    /**
     * 数据权限请求参数
     */
    private Object[] args;
    /**
     * 数据访问限定类型
     */
    private DataScope dataScope;
    /**
     * sql语句中别名字段
     */
    private String dialect;
    /**
     * 分页
     */
    Pager pager;
    /**
     * permit方法
     */
    PermitMethod permitMethod;
    /**
     * 注入器
     */
    PermitProceedInject permitProceedInject;
    /**
     * 权限深度
     */
    Integer deepIn = 0;
}
