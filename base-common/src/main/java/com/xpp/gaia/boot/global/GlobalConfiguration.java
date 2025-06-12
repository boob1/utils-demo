package com.xpp.gaia.boot.global;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 全局拦截器配置
 *
 * @author Akira
 * @since 2021/11/8
 */
@Slf4j
@Configuration
public class GlobalConfiguration implements WebMvcConfigurer {

    public GlobalConfiguration() {
        log.info("Initializing Gaia Global Configuration");
    }

    @Bean
    public GlobalInterceptor globalInterceptor() {
        return new GlobalInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(globalInterceptor())
                .order(0)
                .addPathPatterns("/**")
                .excludePathPatterns("/*.html", "/static/**", "/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg",
                        "/**/*.jpeg", "/**/*.gif", "/**/fonts/*", "/*.ico", "/swagger*", "/swagger*/**/*, /error");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

}
