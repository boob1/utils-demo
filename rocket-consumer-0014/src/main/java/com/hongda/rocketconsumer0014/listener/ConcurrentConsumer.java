package com.hongda.rocketconsumer0014.listener;

import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(
        topic = "concurrent-topic",
        consumerGroup = "concurrent-group",
        consumeMode = ConsumeMode.CONCURRENTLY  // 显式声明并发模式
)
public class ConcurrentConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // 多线程并行处理
        System.out.println("线程" + Thread.currentThread().getId() + "处理消息: " + message);
    }
}

