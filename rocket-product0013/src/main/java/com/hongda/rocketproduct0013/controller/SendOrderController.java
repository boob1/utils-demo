package com.hongda.rocketproduct0013.controller;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:9.顺序消息
 *
 */
@RestController
public class SendOrderController {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @GetMapping("/order")
    public String sendOrder() {
        // 相同orderId的消息会被分配到同一个队列
        for (int i = 1; i <= 5; i++) {
            // 创建消息内容
            String content = "订单[" + i + "]状态变更：步骤";

            // 使用MessageBuilder构建消息
            Message<String> message = MessageBuilder
                    .withPayload(content)
                    .build();

            try {
                // 发送顺序消息
                // 参数1：主题
                // 参数2：消息对象
                // 参数3：路由键（相同业务ID使用相同路由键，确保消息发到同一队列）
                rocketMQTemplate.syncSendOrderly(
                        "order3-topic",  // 主题
                        message,         // 消息内容
                        i +""         // 路由键（如订单ID）
                );

            } catch (Exception e) {
                e.printStackTrace();
                return "顺序消息发送失败";
            }
        }
        return "顺序消息已发送";



    }



}
