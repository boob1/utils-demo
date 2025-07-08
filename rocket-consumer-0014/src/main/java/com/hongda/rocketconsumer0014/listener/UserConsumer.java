package com.hongda.rocketconsumer0014.listener;

import com.hongda.rocketconsumer0014.entity.User;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Service
@RocketMQMessageListener(
        topic = "user-topic",
        consumerGroup = "user-consumer-group"
)
public class UserConsumer implements RocketMQListener<User> {
    @Override
    public void onMessage(User user) {
        System.out.println("收到用户: " + user.getName());
    }
}
