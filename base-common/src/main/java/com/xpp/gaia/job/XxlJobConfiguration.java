package com.xpp.gaia.job;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Xxl-Job配置
 *
 * @author Akira
 * @since 2022/2/9
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(XxlJobProperties.class)
@ConditionalOnClass(name = {"com.xxl.job.core.context.XxlJobContext"})
@ConditionalOnProperty(prefix = "xxl.job.executor", name = "appname")
public class XxlJobConfiguration {

    @Autowired
    XxlJobProperties xxlJobProperties;

    public XxlJobConfiguration() {
        log.info("Initializing Gaia Xxl-Job");
    }

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        if (!StringUtils.isBlank(xxlJobProperties.getAccessToken())) {
            xxlJobSpringExecutor.setAdminAddresses(xxlJobProperties.getAccessToken());
        }
        if (!StringUtils.isBlank(xxlJobProperties.getAdmin().getAddresses())) {
            xxlJobSpringExecutor.setAdminAddresses(xxlJobProperties.getAdmin().getAddresses());
        }
        xxlJobSpringExecutor.setAppname(xxlJobProperties.getExecutor().getAppname());
        if (!StringUtils.isBlank(xxlJobProperties.getExecutor().getIp())) {
            xxlJobSpringExecutor.setIp(xxlJobProperties.getExecutor().getIp());
        }
        xxlJobSpringExecutor.setPort(xxlJobProperties.getExecutor().getPort());
        xxlJobSpringExecutor.setLogPath(xxlJobProperties.getExecutor().getLogPath());
        xxlJobSpringExecutor.setLogRetentionDays(xxlJobProperties.getExecutor().getLogRetentionDays());
        return xxlJobSpringExecutor;
    }
}
