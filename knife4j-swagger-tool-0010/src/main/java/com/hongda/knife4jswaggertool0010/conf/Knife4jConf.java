package com.hongda.knife4jswaggertool0010.conf;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description 配置接口文档基本信息
 * @Author lyb
 * @Date 2025/5/8 14:29
 */
@Configuration
public class Knife4jConf {

  @Bean
  public OpenAPI openAPI() {
    //配置接口文档基本信息
    return new OpenAPI().info(this.getApiInfo());

  }

  private Info getApiInfo() {
    return new Info()
        //配置文档标题
        .title("SpringBoot3集成Knife4j")
        //配置文档描述
        .description("SpringBoot3集成Knife4j示例文档")
        //配置作者信息
        .contact(new Contact().name("程序员晓凡").url("https://www.xiezhrspace.cn")
        .email("1666397814@163.com"))
        //配置License许可证信息
        .license(new License().name("Apache2.0").url("https://www.xiezhrspace.cn"))
        //概述信息
        .summary("SpringBoot3集成Knife4j示例文档aaa")
        .termsOfService("https://www.xiezhrspace.cn")
        //配置版本号
        .version("2.0");
  }
}
