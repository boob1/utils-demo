package com.hongda.rocketproduct0013.controller;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:5.同步消息（可靠但性能较低）
 * 适用于重要通知、支付结果等需要确认的场景
 */
@RestController
public class TransactionController {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("/transaction")
    public String sendTransaction() {
        // 发送事务消息
        // 参数1：事务消息分组
        // 参数2：主题:标签
        // 参数3：消息体
        // 参数4：额外参数（可选）
 /*       rocketMQTemplate.sendMessageInTransaction(
                "tx-group",
                "tx-topic",
                MessageBuilder.withPayload("事务消息").build(),
                null
        );
       */
        return "事务消息已提交";
    }

}
