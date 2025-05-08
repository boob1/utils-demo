package com.hongda.knife4jswaggertool0010.conf;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description 通过配置类Knife4jConfig添加两个分组
 * @Author lyb
 * @Date 2025/5/8 15:49
 */
@Configuration
public class SpringDocConfig {
  @Bean("common")
  public GroupedOpenApi webGroupApi(){
    return GroupedOpenApi.builder().group("common")
        .pathsToMatch("/common/**")
        .build();
  }

  @Bean("admin")
  public GroupedOpenApi adminGroupApi(){
    return GroupedOpenApi.builder().group("admin")
        .pathsToMatch("/admin/**")
        .build();
  }
}
