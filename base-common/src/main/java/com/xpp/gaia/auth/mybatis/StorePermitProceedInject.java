package com.xpp.gaia.auth.mybatis;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.xpp.gaia.auth.PermitFilterable;
import com.xpp.gaia.auth.bean.AuthDataCommonBean;
import com.xpp.gaia.auth.bean.AuthRequestParam;
import com.xpp.gaia.mybatis.MybatisInvocationWrapper;
import com.xpp.gaia.mybatis.permission.PermitBean;
import com.xpp.gaia.mybatis.permission.PermitMethod;
import com.xpp.gaia.mybatis.permission.PermitProceedInject;
import com.xpp.gaia.toolkit.ActionResult;
import com.xpp.gaia.toolkit.Pager;
import com.xpp.gaia.toolkit.utils.JsonUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

/**
 * 门店数据权限注入器
 *
 * @author Akira
 * @since 2022/3/20
 */
@Slf4j
public class StorePermitProceedInject implements PermitProceedInject {

    @Override
    public Object inject(MybatisInvocationWrapper invocationWrapper, PermitBean permitBean) throws Throwable {
        // 优先执行原始sql
        Object retVal = invocationWrapper.getInvocation().proceed();
        if (permitBean.getDeepIn() == 0) {
            return retVal;
        }
        List<PermitFilterable> list = (List<PermitFilterable>) retVal;
        if (CollectionUtils.isEmpty(list)) {
            Page page = this.buildEmptyPageInfo(permitBean);
            return page;
        }
        Set<String> storeSet = list.parallelStream()
                .map(permitFilterable -> permitFilterable.getStoreCode())
                .collect(Collectors.toSet());
        String[] resultStores = storeSet.toArray(new String[storeSet.size()]);

        AuthRequestParam requestParam = this.buildRequestParamWhenStoreSearch(permitBean);
        requestParam.setStoreArr(resultStores);
        Pager pager = PermitMethod.buildMockPager(requestParam);
        AuthPermitMethod permitMethod = (AuthPermitMethod) permitBean.getPermitMethod();
        log.info(" ==>  Preparing: 根据权限获取门店");
        log.info(" ==>  Parameters: {}", JsonUtil.toJson(storeSet));
        ActionResult<PageInfo<AuthDataCommonBean>> storeResult = permitMethod.getAuthDataAPIs().storeSearch(pager);
        log.info(" <==      Total: {}", storeResult.isSuccess() ? storeResult.getData().getList().size() : 0);
        List<PermitFilterable> newList = new ArrayList<>();
        if (storeResult.isSuccess()) {
            // 数据比对
            List<AuthDataCommonBean> storeList = storeResult.getData().getList();
            list.stream().forEach(bean -> {
                storeList.parallelStream().forEach(store -> {
                    if (store.getCode() != null && store.getCode().equals(bean.getStoreCode())) {
                        newList.add(bean);
                        return;
                    }
                });
            });
            if (permitBean.getPager() != null) {
                int startIndex = 0;
                int endIndex = 0;
                List<PermitFilterable> data = new ArrayList<>();
                if (newList.size() > 0) {
                    startIndex = (permitBean.getPager().getPageNum() - 1) * permitBean.getPager().getPageSize();
                    endIndex = startIndex + permitBean.getPager().getPageSize();
                    endIndex = (endIndex > newList.size()) ? newList.size() : endIndex;
                    data = newList.subList(startIndex, endIndex);
                }
                Page page = new Page();
                page.addAll(data);
                page.setStartRow(startIndex);
                page.setEndRow(endIndex);
                page.setPageNum(permitBean.getPager().getPageNum());
                page.setPageSize(permitBean.getPager().getPageSize());
                page.setTotal(newList.size());
                return page;
            }
        }
        return newList;
    }

    private AuthRequestParam buildRequestParamWhenStoreSearch(PermitBean permitBean) {
        if (permitBean.getArgs() == null || permitBean.getArgs().length == 0) {
            return new AuthRequestParam();
        }
        return (AuthRequestParam) permitBean.getArgs()[0];
    }

    private Page buildEmptyPageInfo(PermitBean permitBean) {
        Page page = new Page();
        page.setStartRow(1);
        page.setEndRow(1);
        page.setPageNum(permitBean.getPager().getPageNum());
        page.setPageSize(permitBean.getPager().getPageSize());
        page.setTotal(0);
        return page;
    }
}
