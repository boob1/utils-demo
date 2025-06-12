package com.xpp.gaia.http;

import java.util.ArrayList;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

/**
 * RestTemplate全局配置
 *
 * @author Akira
 * @since 2021/11/8
 */
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "gaia.http.rest-template")
@ConditionalOnMissingBean(RestTemplate.class)
public class RestTemplateGlobalConfiguration {

    /**
     * 自定义RestTemplate拦截器
     */
    @Resource
    public CustomRestTemplateInterceptors customRestTemplateInterceptors;

    /**
     * 自定义RestTemplate MessageConverter
     */
    @Resource
    public CustomRestTemplateMessageConverters customRestTemplateMessageConverters;

    /**
     * RestTemplate默认Gaia拦截器
     *
     * @return ClientHttpRequestInterceptor
     */
    @Bean
    public ClientHttpRequestInterceptor restTemplateGlobalInterceptor() {
        return new RestTemplateGlobalInterceptor();
    }

    /**
     * RestTemplate默认Gaia拦截器栈
     *
     * @return CustomRestTemplateInterceptors
     */
    @ConditionalOnMissingBean(CustomRestTemplateInterceptors.class)
    @Bean
    public CustomRestTemplateInterceptors interceptors() {
        CustomRestTemplateInterceptors interceptors = new CustomRestTemplateInterceptors();
        interceptors.interceptors = new ArrayList<>(1);
        interceptors.interceptors.add(restTemplateGlobalInterceptor());
        return interceptors;
    }

    /**
     * RestTemplate默认Gaia-Converter
     *
     * @return CustomRestTemplateMessageConverters
     */
    @ConditionalOnMissingBean(CustomRestTemplateMessageConverters.class)
    @Bean
    public CustomRestTemplateMessageConverters converters() {
        CustomRestTemplateMessageConverters converts = new CustomRestTemplateMessageConverters();
        converts.converters = new ArrayList<>(1);
        converts.converters.add(new GaiaMappingJackson2HttpMessageConverter());
        return converts;
    }

    /**
     * 注入带负载均衡器的RestTemplate
     *
     * @return RestTemplate
     */
    @ConditionalOnClass({LoadBalancerAutoConfiguration.class})
    @LoadBalanced
    @Bean
    @Qualifier("restTemplateBalanced")
    public RestTemplate restTemplateBalanced() {
        log.info("Initializing Gaia RestTemplate With LoadBalance");
        return buildTemplate();
    }

    /**
     * 注入不带负载均衡器的RestTemplate
     *
     * @return RestTemplate
     */
    //@ConditionalOnMissingClass({"org.springframework.cloud.client.loadbalancer.LoadBalancerAutoConfiguration"})
    @Bean
    @Qualifier("restTemplate")
    public RestTemplate restTemplate() {
        log.info("Initializing Gaia RestTemplate");
        return buildTemplate();
    }

    protected RestTemplate buildTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        // 可以添加消息转换
        if (customRestTemplateMessageConverters.converters != null) {
            restTemplate.getMessageConverters().addAll(customRestTemplateMessageConverters.converters);
        }
        // 增加后置拦截器
        if (!CollectionUtils.isEmpty(customRestTemplateInterceptors.interceptors)) {
            restTemplate.setInterceptors(customRestTemplateInterceptors.interceptors);
        }
        return restTemplate;
    }

}
