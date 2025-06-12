package com.xpp.gaia.mybatisplus;

import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.incrementer.OracleKeyGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MybatisPlus 启动配置
 *
 * @author Akira
 * @since 2021/11/5
 */
@Slf4j
@Configuration
public class MybatisPlusConfiguration {

    public MybatisPlusConfiguration() {
        log.info("Initializing Gaia Mybatis-Plus Configuration");
    }

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return new MybatisPlusCustomizers();
    }


    /**
     * Sequence主键自增
     *
     * @return 返回oracle自增类
     */
    @Bean
    @ConditionalOnClass(OracleKeyGenerator.class)
    public OracleKeyGenerator oracleKeyGenerator() {
        return new OracleKeyGenerator();
    }

    /**
     * 自定义字段处理类注入
     *
     * @return 自定义字段处理类
     */
    @Bean
    public CustomMetaObjectHandler customMetaObjectHandler() {
        return new CustomMetaObjectHandler();
    }
}
