package com.xpp.gaia.auth;

import com.xpp.gaia.auth.adapter.SfaSSOAdapter;
import com.xpp.gaia.auth.anno.AuthArgumentResolver;
import com.xpp.gaia.auth.web.AuthFilter;
import com.xpp.gaia.auth.web.AuthorityInterceptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 权限拦截器配置
 *
 * @author Akira
 * @since 2021/11/8
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(AuthProperties.class)
@ConditionalOnProperty(prefix = "xpp.auth.redis", name = "host")
public class AuthConfiguration implements WebMvcConfigurer {

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

    public AuthConfiguration() {
        log.info("Initializing Gaia Auth");
    }

    /**
     * 初始化jedis配置(用在读取sso令牌)
     *
     * @return jedisPoolConfig
     */
    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        AuthProperties.Redis redisConfig = authProperties.getRedis();
        jedisPoolConfig.setMaxTotal(redisConfig.maxTotal == null ? GenericObjectPoolConfig.DEFAULT_MAX_TOTAL : redisConfig.maxTotal);
        jedisPoolConfig.setMaxIdle(redisConfig.maxIdle == null ? GenericObjectPoolConfig.DEFAULT_MAX_IDLE : redisConfig.maxIdle);
        jedisPoolConfig.setMinIdle(redisConfig.minIdle == null ? GenericObjectPoolConfig.DEFAULT_MIN_IDLE : redisConfig.minIdle);
        jedisPoolConfig.setTestWhileIdle(true);
        jedisPoolConfig.setTestOnBorrow(true);
        jedisPoolConfig.setTestOnReturn(true);
        return jedisPoolConfig;
    }

    /**
     * 初始化jedis连接池(用在读取sso令牌)
     *
     * @return
     */
    @Bean
    @Qualifier("authJedisPool")
    public JedisPool jedisPool() {
        AuthProperties.Redis redisConfig = authProperties.getRedis();
        JedisPool pool = new JedisPool(jedisPoolConfig(),
                redisConfig.host, redisConfig.port, redisConfig.timeout, redisConfig.password, redisConfig.database);
        return pool;
    }

    /**
     * 初始化过滤器
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = "xpp.auth", name = "authz", havingValue = "true")
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
        AuthorityInterceptor authorityInterceptor = new AuthorityInterceptor();
        SfaSSOAdapter.authJedisPool = jedisPool();
        return authorityInterceptor;
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
