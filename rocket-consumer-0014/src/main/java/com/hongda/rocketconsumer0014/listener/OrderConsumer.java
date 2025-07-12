package com.hongda.rocketconsumer0014.listener;

import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(
        topic = "shunXu-topic",
        consumerGroup = "shunXu-consumer-group",
        consumeMode = ConsumeMode.ORDERLY  // 关键配置
)
public class OrderConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        System.out.println("顺序消费: " + message);
    }
}
