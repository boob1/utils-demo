package com.xpp.gaia.mybatis;

import com.github.pagehelper.PageInterceptor;
import java.util.List;
import java.util.Properties;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis拦截器配置
 *
 * @author Akira
 * @since 2022/2/14
 */
@Slf4j
@ConditionalOnClass({SqlSessionFactory.class})
@Configuration
@EnableConfigurationProperties(MybatisMonitorProperties.class)
public class MybatisInterceptorConfiguration {

    @Autowired
    private List<SqlSessionFactory> sqlSessionFactoryList;

    @Autowired
    MybatisMonitorProperties mybatisMonitorProperties;

    public MybatisInterceptorConfiguration() {
        log.info("Initializing Gaia Mybatis Configuration");
    }

    @Bean
    @ConfigurationProperties(prefix = "pagehelper")
    public Properties pageHelperProperties() {
        return new Properties();
    }

    @PostConstruct
    public void initQueryInterceptor() {
        PageInterceptor pageInterceptor = new PageInterceptor();
        pageInterceptor.setProperties(pageHelperProperties());
        SqlMonitorInterceptor sqlMonitorInterceptor = new SqlMonitorInterceptor();
        sqlMonitorInterceptor.setSlowSqlMonitorProperties(mybatisMonitorProperties);
        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactoryList) {
            // 顶级拦截器
            sqlSessionFactory.getConfiguration().addInterceptor(sqlMonitorInterceptor);
            // 确保分页插件先插入，mybatis先插入的后执行
            sqlSessionFactory.getConfiguration().addInterceptor(pageInterceptor);
            // 权限拦截器
            sqlSessionFactory.getConfiguration().addInterceptor(new QueryPermitInterceptor());
            // 数据更新拦截器
            sqlSessionFactory.getConfiguration().addInterceptor(new UpdateInjectInterceptor());

        }
    }

}
