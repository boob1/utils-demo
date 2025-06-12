package com.xpp.gaia.mybatis.permission;

import com.xpp.gaia.auth.DataScope;
import org.apache.commons.lang3.StringUtils;

/**
 * 默认permit方法
 *
 * @author Akira
 * @since 2022/3/20
 */
public class DefaultPermitMethod implements PermitMethod {

    /**
     * @see PermitMethod#permit(DataScope dataScope, String dialect, Object[] args)
     */
    public PermitBean permit(DataScope dataScope,
                             String dialect,
                             Object[] args) {
        PermitBean permitBean = new PermitBean();
        permitBean.setDataScope(dataScope);
        permitBean.setPermitMethod(this);
        permitBean.setDialect(StringUtils.isBlank(dialect) ? dataScope.getDialect() : dialect);
        permitBean.setArgs(args);
        PermitMethod.handlePage(permitBean);
        return permitBean;
    }
}
