package com.hongda.rocketconsumer0014.listener;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RocketMQMessageListener(
        topic = "async-topic",
        consumerGroup = "async-consumer-group"
)
public class AsyncConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String name) {
        System.out.println("异步收到消息: " + name);
    }
}