package com.xpp.gaia.mybatis;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;

/**
 * Gaia 监控配置参数
 *
 * @author Akira
 * @since 2022/3/13
 */
@Data
@ConfigurationProperties("xpp.monitor")
@RefreshScope
public class MybatisMonitorProperties {

    @Value("${xpp.monitor.slowsql-limit:1000}")
    private Integer slowsqlLimit = 500;
}
