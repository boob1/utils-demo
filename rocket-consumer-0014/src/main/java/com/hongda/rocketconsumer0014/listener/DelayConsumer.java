package com.hongda.rocketconsumer0014.listener;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(
        topic = "delay-topic",
        consumerGroup = "delay-consumer-group"
)
public class DelayConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {

        System.out.println("延迟消费收到消息: " + message);
    }
}
