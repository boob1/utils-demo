package com.xpp.gaia.boot.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * SpringUtil
 *
 * @author Akira
 * @since 2023/1/6
 */
@Slf4j
@Configuration
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext = null;
    private static String PROFILE = null;
    public SpringUtil() {
        log.info("SpringUtil......................");
    }

    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = applicationContext;
        }
    }

    /// 获取当前环境
    public static String getActiveProfile() {
        if (PROFILE == null) {
            PROFILE = applicationContext.getEnvironment().getActiveProfiles()[0];
        }
        return PROFILE;
    }
    //通过name获取 Bean
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    // 获取 spring.application.name 属性
    public static String getSpringApplicationName() {
        return applicationContext.getEnvironment().getProperty("spring.application.name");
    }
}
