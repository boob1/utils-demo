package com.xpp.gaia.auth.web;

import static com.xpp.gaia.auth.AuthConfiguration.AUTH_TOKEN;
import static com.xpp.gaia.auth.AuthConfiguration.AUTH_USER;
import static com.xpp.gaia.boot.global.GlobalInterceptor.COMMON_REQUEST_ATTR_LOADER;
import static com.xpp.gaia.boot.global.GlobalInterceptor.LOG_SYMBOL_STEP;
import static com.xpp.gaia.boot.global.GlobalInterceptor.tokenVar;
import static com.xpp.gaia.boot.measure.MeasureInterceptor.COMMON_REQUEST_ATTR_ACCOUNT;
import static com.xpp.gaia.toolkit.action.ActionHandler.assertCheck;

import com.xpp.gaia.auth.AuthProperties;
import com.xpp.gaia.auth.AuthUser;
import com.xpp.gaia.auth.UserAuthAPIs;
import com.xpp.gaia.auth.anno.UnAuth;
import com.xpp.gaia.toolkit.ActionResult;
import com.xpp.gaia.toolkit.action.ActionProcessException;
import com.xpp.gaia.toolkit.utils.JsonUtil;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 权限拦截器
 *
 * @author Akira
 * @since 2021/11/8
 */
@Slf4j
public class AuthorityInterceptor implements HandlerInterceptor {

    @Autowired(required = false)
    AuthUser customAuthUser;
    @Autowired(required = false)
    AuthProperties authProperties;

    @Autowired(required = false)
    UserAuthAPIs userAuthAPIs;

    public static ThreadLocal<AuthUser> authUser = new InheritableThreadLocal<>();
    public static final String RELOGIN_CODE = "401";

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        String token = tokenVar.get();
        // swagger调试时允许没有令牌
        if (this.isUseDebugData(request, token)) {
            authUser.set(customAuthUser);
            return true;
        }
        // 支持注解跳过验证
        if (this.unAuthMethodHandle((HandlerMethod) handler)) {
            return true;
        }
        assertCheck(token != null, doReLogin("请使用微信或App访问"));
        assertCheck(request.getAttribute(COMMON_REQUEST_ATTR_LOADER) != null, "未定义请求来源");

        AuthUser user = null;
        HttpSession seesion = request.getSession();
        if (this.isUseSeesionData(seesion, token)) {
            user = (AuthUser) seesion.getAttribute(AUTH_USER);
        } else {
            ActionResult<AuthUser> authResult = userAuthAPIs.validAndGetAuthV2(token);
            assertCheck(authResult.isSuccess(), new ActionProcessException("401", authResult.getMessage()));
            user = authResult.getData();
            assertCheck(user != null, new ActionProcessException("401", "无效的访问令牌，请尝试重新登录"));
            seesion.setAttribute(AUTH_USER, user);
        }
        request.setAttribute(COMMON_REQUEST_ATTR_ACCOUNT, user.getAccount());
        // 刷新线程中用户信息
        authUser.set(user);
        log.info("{} 登录用户信息: [{}]", LOG_SYMBOL_STEP, JsonUtil.toJson(user));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                @Nullable Exception ex) {
        authUser.remove();
    }

    /**
     * 是否启用debug
     *
     * @param request     request
     * @param accessToken 令牌
     * @return
     */
    protected boolean isUseDebugData(HttpServletRequest request, String accessToken) {
        String debugTag = authProperties == null ? "debugging" : authProperties.getDebugTag();
        if (accessToken == null
                && (request.getParameterMap().containsKey(debugTag) || request.getHeader(debugTag) != null)) {
            assertCheck(customAuthUser != null, new ActionProcessException("401", "未初始化debug用户"));
            log.info("{} 开启调试{}: [{}]", LOG_SYMBOL_STEP, debugTag, JsonUtil.toJson(customAuthUser));
            return true;
        }
        return false;
    }

    protected Boolean unAuthMethodHandle(HandlerMethod handlerMethod) {
        Method method = handlerMethod.getMethod();
        if (method.isAnnotationPresent(UnAuth.class)) {
//            UnAuth jwtIgnore = method.getAnnotation(UnAuth.class);
            return true;
        }
        return false;
    }

    /**
     * 沿用session中的用户信息，需要符合令牌没有变化的条件
     *
     * @param seesion     seesion
     * @param accessToken 令牌
     * @return
     */
    protected boolean isUseSeesionData(HttpSession seesion, String accessToken) {
        if (seesion.getAttribute(AUTH_USER) != null
                && seesion.getAttribute(AUTH_TOKEN) != null
                && seesion.getAttribute(AUTH_TOKEN).toString().equalsIgnoreCase(accessToken)) {
            return true;
        }
        return false;
    }

    public static ActionProcessException doReLogin(String msg) {
        return new ActionProcessException(RELOGIN_CODE, msg);
    }

}
