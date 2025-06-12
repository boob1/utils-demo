package com.xpp.gaia.auth.web;

import static com.xpp.gaia.boot.global.GlobalInterceptor.LOG_SYMBOL_STEP;

import com.xpp.gaia.auth.AuthProperties;
import com.xpp.gaia.auth.AuthWrapper;
import com.xpp.gaia.auth.bean.AuthRequestParam;
import com.xpp.gaia.toolkit.utils.JsonUtil;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;

/**
 * 权限过滤器
 *
 * @author Akira
 * @since 2022/3/5
 */
@Slf4j
public class AuthFilter implements Filter {

    public static ThreadLocal<AuthWrapper> AUTH_WRAPPER_HOLDER = new InheritableThreadLocal<>();

    private final String KEY_PERMIT_PARAM = "authRequestParam";

    AuthProperties authProperties;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
        log.info("Initializing Gaia Authz");
    }

    public void setAuthProperties(AuthProperties authProperties) {
        this.authProperties = authProperties;
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        RereadableRequestWrapper wrappedRequest = null;
        if (authProperties.getAuthz()) {
            AuthWrapper wrapper = AuthWrapper.builder()
                    .requetPermissionOn(false)
                    .deepIn(0)
                    .build();
            if (request.getContentType() == null) {
                log.debug("请求未定义ContentType");
            }
            if (request.getMethod().equalsIgnoreCase(HttpMethod.POST.toString())
                    && request.getContentType() != null
                    && request.getContentType().toLowerCase().indexOf("application/json") > -1) {
                wrappedRequest = new RereadableRequestWrapper(request, KEY_PERMIT_PARAM);
                String authJson = wrappedRequest.getPopedValue();
                if (authJson != null) {
                    log.info("{} [authRequest]参数: {}", LOG_SYMBOL_STEP, authJson);
                    wrapper.setAuthRequestParam(JsonUtil.fromJson(authJson, AuthRequestParam.class));
                    wrapper.predictDataScope();
                    wrapper.setRequetPermissionOn(wrapper.getDataScope() == null ? false : true);
                    wrapper.predictDataScope();
                }
            }
            AUTH_WRAPPER_HOLDER.set(wrapper);
        }
        filterChain.doFilter(wrappedRequest != null ? wrappedRequest : request, servletResponse);
    }

    @Override
    public void destroy() {
        AUTH_WRAPPER_HOLDER.remove();
        Filter.super.destroy();
    }
}
