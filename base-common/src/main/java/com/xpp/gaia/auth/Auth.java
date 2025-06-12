package com.xpp.gaia.auth;

import static com.xpp.gaia.auth.AuthConfiguration.AUTH_USER;
import static com.xpp.gaia.auth.AuthProcessException.IllegalAuthWrapper;
import static com.xpp.gaia.auth.AuthProcessException.UnloginWhenAuth;
import static com.xpp.gaia.auth.web.AuthFilter.AUTH_WRAPPER_HOLDER;
import static com.xpp.gaia.auth.web.AuthorityInterceptor.authUser;
import static com.xpp.gaia.boot.global.GlobalInterceptor.loadVar;
import static com.xpp.gaia.boot.global.GlobalInterceptor.tokenVar;
import static com.xpp.gaia.toolkit.action.ActionHandler.assertCheck;
import static com.xpp.gaia.toolkit.action.ActionHandler.conditionCheck;

import com.xpp.gaia.auth.bean.AuthRequestParam;
import com.xpp.gaia.auth.mybatis.AuthPermitMethod;
import com.xpp.gaia.auth.mybatis.StorePermitProceedInject;
import com.xpp.gaia.boot.global.LoadScene;
import com.xpp.gaia.mybatis.QueryPermitInterceptor;
import com.xpp.gaia.mybatis.permission.DefaultPermitMethod;
import com.xpp.gaia.mybatis.permission.DefaultPermitProceedInject;
import com.xpp.gaia.mybatis.permission.PermitMethod;
import com.xpp.gaia.mybatis.permission.PermitProceedInject;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Auth工具类
 *
 * @author Akira
 * @since 2022/1/6
 */
@Slf4j
public class Auth {

    public static AuthDataAPIs authDataAPIs;

    private static ThreadLocal<String> permitTemp = new InheritableThreadLocal<>();

    /**
     * 获取当前用户令牌
     *
     * @return 令牌
     */
    public static String getToken() {
        return tokenVar.get();
    }

    public static void permitBySql() {
        permitTemp.set("_SQL_PERMIT");
    }

    public static Boolean claimedBySql() {
        return permitTemp.get() != null;
    }

    /**
     * 获取当前用户对象
     *
     * @return 用户对象AuthUser
     */
    public static AuthUser getUser() {
        AuthUser user = authUser.get();
        if (user == null) {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (servletRequestAttributes == null) {
                return null;
            }
            HttpServletRequest request = servletRequestAttributes.getRequest();
            user = (AuthUser) request.getSession().getAttribute(AUTH_USER);
        }
        return user;
    }

    public static void setUser(AuthUser user) {
        authUser.set(user);
    }

    /**
     * 获取用户类型
     *
     * @return 类型枚举
     */
    public static UserType getUserType() {
        return UserType.getThisUserType(getUser().getUType());
    }

    public static String getLoadFrom() {
        return loadVar.get();
    }

    /**
     * 是否拥有总部权限
     *
     * @return 是否拥有总部权限
     */
    public static Boolean hasAllPermission() {
        AuthUser user = getUser();
        if (user == null) {
            return false;
        }
        return user.getHasAllPermission() == null ? false : user.getHasAllPermission();
    }

    /**
     * 获取当前请求的Auth包装类
     *
     * @return Auth包装类
     */
    public static AuthWrapper volidAndGetRequestAuth() {
        if (!AuthConfiguration.izAuthzOn) {
            throw new AuthProcessException(AuthProcessException.AuthControlOff);
        }
        AuthWrapper authWrapper = AUTH_WRAPPER_HOLDER.get();
        if (authWrapper == null) {
            // 正常情况下AuthWrapper都会存在
            throw new AuthProcessException(IllegalAuthWrapper);
        }
        return authWrapper;
    }

    /**
     * http请求中是否存在权限参数
     *
     * @return true：不存在参数 false：存在
     */
    public static Boolean hasNoParamAuthRequest() {
        AuthWrapper authWrapper = AUTH_WRAPPER_HOLDER.get();
        if (authWrapper == null) {
            return true;
        }
        if (!authWrapper.getRequetPermissionOn() || authWrapper.getArgs() == null) {
            return true;
        }
        return false;
    }

    /**
     * @see Auth#permit(DataScope dataScope, String dialect)
     */
    public static void permit() {
        Auth.permit(null, null);
    }

    /**
     * @see Auth#permit(DataScope dataScope, String dialect)
     */
    public static void permit(DataScope dataScope) {
        Auth.permit(dataScope, null);
    }

    /**
     * @see Auth#permit(DataScope dataScope, String dialect)
     */
    public static void permit(String dialect) {
        Auth.permit(null, dialect);
    }

    /**
     * 开启权限片断
     *
     * @param dataScope 数据范围
     * @param dialect   sql别名
     */
    public static void permit(DataScope dataScope, String dialect) {
        if (!AuthConfiguration.izAuthzOn) {
            throw new AuthProcessException(AuthProcessException.AuthControlOff);
        }
        AuthUser authUser = Auth.getUser();
        if (authUser == null) {
            throw new AuthProcessException(UnloginWhenAuth);
        }
        // authWrapper一般用在后台管理界面，配合权限前端通用组件使用
        AuthWrapper authWrapper = AUTH_WRAPPER_HOLDER.get();
        // 全部权限且无入参，权限片断不处理
        if (hasAllPermission() && hasNoParamAuthRequest()) {
            return;
        }
        PermitMethod permitMethod; // 前置处理器
        PermitProceedInject permitProceedInject;  // 后置处理器
        Object[] paramArgs;
        /**
         * 权限片断分为启用参数和不启用参数的2种case
         */
        Boolean useAuthWrapper = false;
        if (authWrapper != null && authWrapper.getRequetPermissionOn()) {
            useAuthWrapper = true;
            if (dataScope != null) {
                // 注意: 这里是会更新deepIn的
                authWrapper.setDataScope(dataScope);
            }
        }
        if (useAuthWrapper) {
            /*
                入参的深度与dataScope的深度比对 -> deepIn
                1、deepIn == 0，则使用默认方式，即在sql后自动拼接in
                2、deepIn <> 0，则判定需要使用哪种权限注入器
             */
            if (authWrapper.getDeepIn() == 0) {
                permitMethod = new DefaultPermitMethod();
                permitProceedInject = new DefaultPermitProceedInject();
                paramArgs = authWrapper.getArgs();
            } else {
                permitMethod = getAuthPermitMethod();
                permitProceedInject = getAndMatchPermitProceedInject(dataScope);
                paramArgs = new Object[]{authWrapper.getAuthRequestParam()};
            }
        } else {
            permitMethod = getAuthPermitMethod();
            permitProceedInject = getAndMatchPermitProceedInject(dataScope);
            AuthRequestParam params = rebuildAuthRequestParams(dataScope);
            paramArgs = params == null ? null : new Object[]{params};
        }
        QueryPermitInterceptor.permit(permitMethod,
                permitProceedInject,
                dataScope,
                dialect,
                paramArgs);
        if (permitTemp.get() != null) {
            permitTemp.remove();
        }
        return;
    }

    private static AuthRequestParam rebuildAuthRequestParams(DataScope dataScope) {
        AuthUser authUser = Auth.getUser();
        // 必须来自微信且为经销商业代类型
        if (getLoadFrom().equals(LoadScene.DMS_APP) && authUser.getUserType().equals("经销商业代")) {
            // 微信用户权限控制，并且会默认赋DataScope
            if (dataScope == null) {
                dataScope = DataScope.DEALER;
            }
            conditionCheck(dataScope != DataScope.STORE && dataScope != DataScope.DEALER, "微信模式不支持到组织或人员");
            // 微信用户生成AuthRequestParam
            AuthRequestParam params = new AuthRequestParam();
            assertCheck(!CollectionUtils.isEmpty(authUser.getUserDealers()), "用户未配置经销商关系");
            params.setDealerArr(authUser.getUserDealers().stream().map(e -> e.getDealerCode()).toArray(size -> new String[size]));
            return params;
        }
        return null;
    }

    private static PermitProceedInject getAndMatchPermitProceedInject(DataScope dataScope) {
        if (dataScope == DataScope.STORE && AuthPermitMethod.needStoreLoadOptimize()) {
            return new StorePermitProceedInject();
        }
        return new DefaultPermitProceedInject();
    }

    private static AuthPermitMethod getAuthPermitMethod() {
        AuthPermitMethod authPermitMethod = new AuthPermitMethod();
        authPermitMethod.setAuthDataAPIs(authDataAPIs);
        return authPermitMethod;
    }

}
