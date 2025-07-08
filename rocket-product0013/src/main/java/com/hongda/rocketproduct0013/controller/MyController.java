package com.hongda.rocketproduct0013.controller;

import jakarta.annotation.Resource;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @Description:基础测试
 */
@RestController
public class MyController {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("/send")
    public String send() {
        rocketMQTemplate.convertAndSend("test-topic", "Hello World!你好测试第一次发送消息");
        return "消息已发送";
    }
}

