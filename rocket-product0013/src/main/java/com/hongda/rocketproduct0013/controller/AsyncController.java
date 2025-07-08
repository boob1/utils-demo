package com.hongda.rocketproduct0013.controller;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:6.异步消息（高性能）
 * 适合日志收集等允许少量丢失的场景
 */
@RestController
public class AsyncController {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @GetMapping("/async")
    public String sendAsync() {
        rocketMQTemplate.asyncSend("async-topic", "异步消息", new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println("发送成功：" + sendResult);
            }

            @Override
            public void onException(Throwable e) {
                System.err.println("发送失败：" + e.getMessage());
            }
        });
        return "异步请求已提交";
    }

}
