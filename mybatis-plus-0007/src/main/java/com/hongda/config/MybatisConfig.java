package com.hongda.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description
 * @Author mybatisPlus分页
 * @Date 2024/8/27 11:17
 */
@Configuration
public class MybatisConfig {


  @Bean
  public MybatisPlusInterceptor mybatisPlusInterceptor(){
    MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
    PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(
        DbType.MYSQL);
    paginationInnerInterceptor.setMaxLimit(1000L);
    interceptor.addInnerInterceptor(paginationInnerInterceptor);
    return interceptor;

  }

}
