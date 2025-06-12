package com.xpp.gaia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.xpp.gaia.boot"})
public class BaseCommonApplication {

  public static void main(String[] args) {
    SpringApplication.run(BaseCommonApplication.class, args);
  }

}
