package com.hongda.rocketproduct0013.controller;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:10.事务消息发送
 *
 */
@RestController
public class TransactionController {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("/transaction")
    public String sendTransaction() {
        rocketMQTemplate.sendMessageInTransaction(
                "tx-topic" ,
                buildTransactionMessage("事务消息....."),
                null
        );
        return "事务消息已提交";
    }
    private Message<String> buildTransactionMessage(String content) {
        return MessageBuilder
                .withPayload(content)
                .setHeader("businessType", "transaction")
                .build();
    }


}
