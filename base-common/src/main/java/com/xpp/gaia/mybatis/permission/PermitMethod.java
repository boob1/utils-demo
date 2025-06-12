package com.xpp.gaia.mybatis.permission;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xpp.gaia.auth.DataScope;
import com.xpp.gaia.auth.bean.AuthRequestParam;
import com.xpp.gaia.toolkit.Pager;

/**
 * Permit时使用的方法
 *
 * @author Akira
 * @since 2022/3/20
 */
public interface PermitMethod {

    /**
     * 以入参控制数据权限
     *
     * @param dataScope 数据范围
     * @param dialect   字段别名
     * @param args      参数
     * @return PermitBean
     */
    PermitBean permit(DataScope dataScope, String dialect, Object[] args);

    static void handlePage(PermitBean permitBean) {
        Page page = PageHelper.getLocalPage();
        if (page != null) {
            Pager pager = new Pager(page.getPageSize(), page.getPageNum(), null);
            permitBean.setPager(pager);
            PageHelper.clearPage();
        }
    }

    static Pager buildMockPager(AuthRequestParam requestParam) {
        return new Pager<>(999999, -1, requestParam);
    }
}
