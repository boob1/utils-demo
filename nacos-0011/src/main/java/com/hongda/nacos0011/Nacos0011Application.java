package com.hongda.nacos0011;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient // 启用服务发现
public class Nacos0011Application {

  public static void main(String[] args) {
    SpringApplication.run(Nacos0011Application.class, args);
  }

}
