package com.hongda.config;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description TODO
 * @Author lyb
 * @Date 2024/8/16 16:37
 */
@Configuration
public class WebServerConfig {
  @Bean
  public ServletWebServerFactory servletContainer() {
    TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
    // 自定义配置
    factory.setPort(8080);
    return factory;
  }
}
