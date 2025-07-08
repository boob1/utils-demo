package com.hongda.rocketproduct0013.controller;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:8.延迟消息
 * 延迟时间到达后，Broker 将消息从延迟队列转移到普通队列，消费者可以订阅并消费该消息
 *
 * 发送阶段
 * 客户端（生产者）调用 syncSend() 方法将消息发送到 Broker。
 * 3000 毫秒超时：如果 Broker 在 3000 毫秒内未返回发送结果（成功 / 失败），客户端会中断发送并抛出异常。
 * 存储阶段
 * 如果发送成功，消息会被存储在 Broker 中，并标记为 延迟消息。
 * 3 级延迟：Broker 会根据延迟级别，将消息放入对应的延迟队列中。级别 3 对应 10 秒延迟，即消息会在 10 秒后才会被投递到消费者。
 */
@RestController
public class DelayController {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("/delay")
    public String sendDelay() {
        // 延迟级别：1=1s, 2=5s, 3=10s,..., 18=2h
        rocketMQTemplate.syncSend("delay-topic",
                MessageBuilder.withPayload("延迟消息").build(),
                3000,  // 发送超时
                3      // 延迟级别3（10秒后投递）
        );
        return "延迟消息已发送";
    }

}
