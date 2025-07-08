package com.hongda.rocketconsumer0014.listener;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RocketMQMessageListener(
        topic = "list-topic",
        consumerGroup = "list-consumer-group"
)
public class ListConsumer implements RocketMQListener<List<String>> {
    @Override
    public void onMessage(List<String> names) {
        names.forEach(name -> System.out.println("收到名字: " + name));
    }
}

