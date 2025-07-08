package com.hongda.rocketconsumer0014.listener;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RocketMQMessageListener(
        topic = "map-topic",
        consumerGroup = "map-consumer-group"
)
public class MapConsumer implements RocketMQListener<Map<String, Integer>> {
    @Override
    public void onMessage(Map<String, Integer> map) {
        // 循环map
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            System.out.println("收到: " + entry.getKey() + ": " + entry.getValue());
        }
    }
}
