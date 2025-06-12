package com.xpp.gaia.auth.mybatis;

import static com.xpp.gaia.auth.web.AuthFilter.AUTH_WRAPPER_HOLDER;

import com.github.pagehelper.PageInfo;
import com.xpp.gaia.auth.Auth;
import com.xpp.gaia.auth.AuthDataAPIs;
import com.xpp.gaia.auth.AuthProcessException;
import com.xpp.gaia.auth.AuthWrapper;
import com.xpp.gaia.auth.DataScope;
import com.xpp.gaia.auth.bean.AuthDataCommonBean;
import com.xpp.gaia.auth.bean.AuthRequestParam;
import com.xpp.gaia.auth.bean.OrgNode;
import com.xpp.gaia.mybatis.permission.PermitBean;
import com.xpp.gaia.mybatis.permission.PermitMethod;
import com.xpp.gaia.toolkit.ActionResult;
import com.xpp.gaia.toolkit.utils.JsonUtil;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

/**
 * Auth permit方法
 *
 * @author Akira
 * @since 2022/3/20
 */
@Slf4j
public class AuthPermitMethod implements PermitMethod {

    @Getter
    @Setter
    AuthDataAPIs authDataAPIs;

    /**
     * @see PermitMethod#permit(DataScope dataScope, String dialect, Object[] args)
     */
    public PermitBean permit(@NotNull DataScope dataScope,
                             String dialect,
                             Object[] args) {
        if (authDataAPIs == null) {
            throw new AuthProcessException("未定义权限数据获取实现类");
        }
        PermitBean permitBean = new PermitBean();
        permitBean.setDataScope(dataScope);
        permitBean.setPermitMethod(this);
        permitBean.setDialect(StringUtils.isBlank(dialect) ? dataScope.getDialect() : dialect);
        AuthRequestParam requestParam = isEmpty(args) ? new AuthRequestParam() : (AuthRequestParam) args[0];
        PermitMethod.handlePage(permitBean);
        switch (dataScope) {
            case STORE:
                AuthWrapper authWrapper = AUTH_WRAPPER_HOLDER.get();
                // 效率优化
                if (needStoreLoadOptimize()) {
                    permitBean.setArgs(args);
                    permitBean.setDeepIn(Auth.hasNoParamAuthRequest() ? -1 : authWrapper.getDeepIn());
                } else {
                    this.buildArgs(dataScope, requestParam,
                            this.authDataAPIs.storeSearch(PermitMethod.buildMockPager(requestParam)));
                    permitBean.setArgs(requestParam.getStoreArr());
                }
                break;
            case EMP:
                this.buildArgs(dataScope, requestParam,
                        this.authDataAPIs.salesmanSearch(PermitMethod.buildMockPager(requestParam)));
                permitBean.setArgs(requestParam.getEmpArr());
                break;
            case DEALER:
                this.buildArgs(dataScope, requestParam,
                        this.authDataAPIs.customerSearch(PermitMethod.buildMockPager(requestParam)));
                permitBean.setArgs(requestParam.getDealerArr());
                break;
            case ORG:
                this.buildArgs(dataScope, requestParam,
                        this.authDataAPIs.orgSearch(false));
                permitBean.setArgs(requestParam.getOrgArr());
                break;
            default:
                break;
        }
        log.info(" <==      Permit Total: {}", permitBean.getArgs() == null ? 0 : permitBean.getArgs().length);
        return permitBean;
    }

    private void buildArgs(DataScope dataScope, AuthRequestParam authRequestParam, ActionResult<?> result) {
        if (!result.isSuccess()) {
            return;
        }
        log.info(" ==>  Preparing: 根据权限获取{}", dataScope);
        log.info(" ==>  Parameters: {}", JsonUtil.toJson(authRequestParam));
        if (dataScope == DataScope.ORG) {
            List<OrgNode> data = (List<OrgNode>) result.getData();
            if (data != null) {
                Set<Integer> orgIds = data.parallelStream().map(o -> Integer.parseInt(o.getId())).collect(Collectors.toSet());
                this.handleEmptySet(orgIds);
                authRequestParam.setOrgArr(orgIds.toArray(new Integer[orgIds.size()]));
            }
        } else {
            PageInfo<AuthDataCommonBean> data = (PageInfo<AuthDataCommonBean>) result.getData();
            Set<String> sets = data.getList().parallelStream().map(bean -> bean.getCode()).collect(Collectors.toSet());
            this.handleEmptySet(sets);
            switch (dataScope) {
                case STORE:
                    authRequestParam.setStoreArr(sets.toArray(new String[sets.size()]));
                    break;
                case EMP:
                    authRequestParam.setEmpArr(sets.toArray(new String[sets.size()]));
                    break;
                case DEALER:
                    authRequestParam.setDealerArr(sets.toArray(new String[sets.size()]));
                    break;
                default:
                    break;
            }
        }
    }

    private boolean isEmpty(Object[] args) {
        if (args == null || args.length == 0) {
            return true;
        }
        return false;
    }

    private void handleEmptySet(Set set) {
        if (CollectionUtils.isEmpty(set)) {
            if (set == null) {
                set = new HashSet<>(1);
            }
            set.add("NIL");
        }
    }

    @Deprecated
    private void flatOrgTree(Map map, Set<Integer> set) {
        if (map.containsKey("id")) {
            set.add(Integer.parseInt(map.get("id").toString()));
        }
        if (map.containsKey("children")) {
            flatOrgTree((Map) map.get("children"), set);
        }
    }

    /**
     * 后置查询优化
     *
     *
     * @return
     * @apiNote 优化：是将门店数据在后台进行比对，而不是在sql中注入
     * 无须优化：当入参中已经包含了某些指定门店/人/经销商 || 手工指定不需要优化
     */
    public static boolean needStoreLoadOptimize() {
        // 被强制声明走sql的，不用优化
        if (Auth.claimedBySql()) {
            return false;
        }
        // 没有“权限入参”或者入参里已经包含了组织、经销商等信息
        AuthWrapper authWrapper = AUTH_WRAPPER_HOLDER.get();
        if (Auth.hasNoParamAuthRequest()
                || (authWrapper != null && authWrapper.getDataScopeInOriginRequest().getDeep() < DataScope.DEALER.getDeep())) {
            return true;
        }
        return false;
    }
}
