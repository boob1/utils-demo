package com.hongda.rocketproduct0013.controller;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:9.顺序消息
 * 并发模式下：多个线程会同时消费不同消息，输出线程 ID 不同。
 * 顺序模式下：同一个队列的消息始终由一个线程串行消费，输出线程 ID 相同。
 */
@RestController
public class SendOrderController {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("/order")
    public String sendOrder() {
        // 相同orderId的消息会被分配到同一个队列
        for (int i = 1; i <= 5; i++) {
            rocketMQTemplate.syncSendOrderly(
                    "shunXu-topic",
                    "顺序消息" + i,
                    "ORDER_001"  // 保证相同订单号的消息顺序
            );
        }
        return "顺序消息已发送";
    }


}
