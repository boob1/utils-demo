package com.xpp.gaia.http;

import static com.xpp.gaia.boot.global.GlobalInterceptor.VAR_ACCESS_TOKEN;
import static com.xpp.gaia.boot.global.GlobalInterceptor.VAR_GLOBAL_TRACE_ID;
import static com.xpp.gaia.boot.global.GlobalInterceptor.tokenVar;
import static com.xpp.gaia.boot.global.GlobalInterceptor.traceVar;

import com.xpp.gaia.auth.AuthProperties;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Feign拦截器
 *
 * @author Akira
 * @since 2021/11/8
 */
@Slf4j
@Configuration
@ConditionalOnClass(name = "org.springframework.cloud.client.discovery.EnableDiscoveryClient")
public class FeignGlobalConfiguration implements RequestInterceptor {

    @Autowired(required = false)
    AuthProperties authProperties;

    public FeignGlobalConfiguration() {
        log.info("Initializing Gaia Feign Interceptor");
    }

    @Override
    public void apply(RequestTemplate requestTemplate) {
        if (traceVar.get() != null) {
            requestTemplate.header(VAR_GLOBAL_TRACE_ID, traceVar.get());
        }
        if (tokenVar.get() != null) {
            requestTemplate.header(VAR_ACCESS_TOKEN, tokenVar.get());
        }
        ServletRequestAttributes servletAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletAttributes != null) {
            String debugTag = authProperties == null ? "debugging" : authProperties.getDebugTag();
            String debugging = servletAttributes.getRequest().getHeader(debugTag) == null ? "" : servletAttributes.getRequest().getHeader(debugTag);
            if (StringUtils.isNotBlank(debugging)) {
                requestTemplate.header(debugTag, debugging);
            }
        }
    }
}
