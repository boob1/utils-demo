package com.hongda.rocketconsumer0014.listener;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@RocketMQMessageListener(
        topic = "tx-topic",
        consumerGroup = "tx-consumer-group"
)
public class TransactionConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {

        System.out.println("事务收到消息: " + message);
    }
}
