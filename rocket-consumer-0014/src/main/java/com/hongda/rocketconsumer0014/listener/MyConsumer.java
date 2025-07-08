package com.hongda.rocketconsumer0014.listener;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(
        topic = "test-topic",
        consumerGroup = "my-consumer-group"
)
public class MyConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {

        System.out.println("收到消息: " + message);
    }
}

