package com.xpp.gaia.boot.measure;

import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * MeasureConfiguration
 *
 * @author Akira
 * @since 2024/2/21
 */
@Configuration
@ConditionalOnProperty(prefix = "spring.influx", name = "url")
@Slf4j
public class MeasureConfiguration implements WebMvcConfigurer {

    @Value("${spring.influx.url}")
    private String influxUrl;
    @Value("${spring.influx.database:meter}")
    private String database;
    @Value("${spring.influx.retentionPolicy:keep_30d}")
    private String retentionPolicy;
    @Value("${spring.influx.logLevel:NONE}")
    private String logLevel;
    @Value("${spring.influx.enableBatch:true}")
    private Boolean enableBatch;
    @Value("${spring.influx.batchNum:100}")
    private Integer batchNum;
    @Value("${spring.influx.batchInterval:60000}")
    private Integer batchInterval;

    public MeasureConfiguration() {
        log.info("Initializing Gaia Measurement");
    }

    @Bean
    public MeasureInterceptor measureInterceptor() {
        return new MeasureInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(measureInterceptor())
                .order(1)
                .addPathPatterns("/**");
    }

    @Bean
    public InfluxDB influxdb() {
        InfluxDB influxDB = InfluxDBFactory.connect(influxUrl);
        try {
            /**
             * 异步插入：
             * enableBatch这里第一个是point的个数，第二个是时间，单位毫秒
             * point的个数和时间是联合使用的，如果满100条或者60 * 1000毫秒
             * 满足任何一个条件就会发送一次写的请求。
             */
            if (enableBatch) {
                influxDB.setDatabase(database).enableBatch(batchNum, batchInterval, TimeUnit.MILLISECONDS);
            }
            // 设置日志输出级别
            influxDB.setLogLevel(InfluxDB.LogLevel.parseLogLevel(logLevel));
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
        } finally {
            // 设置默认策略
            if (!retentionPolicy.equalsIgnoreCase("default")) {
                influxDB.setRetentionPolicy(retentionPolicy);
            }
        }
        return influxDB;
    }
}
