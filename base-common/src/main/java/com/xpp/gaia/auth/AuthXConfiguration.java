package com.xpp.gaia.auth;

import com.xpp.gaia.auth.anno.AuthArgumentResolver;
import com.xpp.gaia.auth.web.AuthFilter;
import com.xpp.gaia.auth.web.AuthorityInterceptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 权限拦截器配置
 *
 * @author Akira
 * @since 2021/11/8
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(AuthProperties.class)
@ConditionalOnProperty(prefix = "xpp", name = "authx")
public class AuthXConfiguration implements WebMvcConfigurer {

    public final static String AUTH_USER = "AUTH_USER";
    public final static String AUTH_TOKEN = "AUTH_TOKEN";
    public static Boolean izAuthzOn = false;

    String[] defaultIncludes = {"/**"};
    String[] defaultExcludes = {"/*.html", "/static/**", "/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg",
            "/**/*.jpeg", "/**/*.gif", "/**/fonts/*", "/*.ico", "/login/*", "/login/**/*", "/swagger*", "/swagger*/**/*"};

    @Autowired
    AuthProperties authProperties;

    @Autowired(required = false)
    @Lazy
    AuthDataAPIs authDataAPIs;

    public AuthXConfiguration() {
        log.info("Initializing Gaia AuthX");
    }

    /**
     * 初始化过滤器
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = "xpp.authx", name = "authz", havingValue = "true")
    public AuthFilter authFilter() {
        AuthFilter filter = new AuthFilter();
        this.authProperties.setAuthz(true);
        filter.setAuthProperties(this.authProperties);
        this.izAuthzOn = this.authProperties.getAuthz();
        return filter;
    }

    /**
     * 过滤器注入
     *
     * @param authFilter
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = "xpp.auth", name = "authz", havingValue = "true")
    public FilterRegistrationBean getFilterRegistrationBean(AuthFilter authFilter) {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(authFilter); //设置过滤器
        filterRegistrationBean.setOrder(1); //设置优先级
        // filterRegistrationBean.addUrlPatterns("/api/*");    添加过滤的url
        filterRegistrationBean.setName("authFilter"); //设置过滤器的名称
        return filterRegistrationBean;
    }

    /**
     * 初始化权限拦截器
     *
     * @return authorityInterceptor
     */
    @Bean
    public AuthorityInterceptor authorityInterceptor() {
        return new AuthorityInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 处理例外路径
        List<String> allExcludes = new ArrayList<>();
        allExcludes.addAll(Arrays.asList(defaultExcludes));
        if (!StringUtils.isBlank(authProperties.getExcludePaths())) {
            List<String> customerList = Arrays.asList(authProperties.getExcludePaths().split(","));
            allExcludes.addAll(customerList);
        }
        // 处理包含路径
        List<String> allIncludes = new ArrayList<>();
        if (!StringUtils.isBlank(authProperties.getIncludePaths())) {
            List<String> customerList = Arrays.asList(authProperties.getIncludePaths().split(","));
            allIncludes.addAll(customerList);
        } else {
            allIncludes.addAll(Arrays.asList(defaultIncludes));
        }

        registry.addInterceptor(authorityInterceptor())
                .order(9)
                .addPathPatterns(allIncludes)
                .excludePathPatterns(allExcludes);
        Auth.authDataAPIs = this.authDataAPIs;
    }

    /**
     * 添加注解Resolver
     *
     * @param resolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthArgumentResolver());
    }
}
