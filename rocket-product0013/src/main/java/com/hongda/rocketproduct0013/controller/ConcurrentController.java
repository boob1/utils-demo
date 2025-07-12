package com.hongda.rocketproduct0013.controller;

import jakarta.annotation.Resource;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @Description:11.声明式并行消费
 * 并发模式下：多个线程会同时消费不同消息，输出线程 ID 不同。
 * 顺序模式下：同一个队列的消息始终由一个线程串行消费，输出线程 ID 相同。
 *
 */
@RestController
public class ConcurrentController {
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("/concurrent")
    public String send() {
        for (int i = 0; i < 10; i++) {
            rocketMQTemplate.convertAndSend("concurrent-topic", "Hello World!你好测试第一次发送消息");

        }
        return "消息已发送";
    }
}
