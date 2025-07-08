package com.hongda.rocketproduct0013.controller;

import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @Description:5.同步消息（可靠但性能较低）
 * 适用于重要通知、支付结果等需要确认的场景
 */
@RestController
public class SyncController {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("/sync")
    public String sendSync() {
        // 同步发送会阻塞直到收到Broker确认
        SendResult result = rocketMQTemplate.syncSend("sync-topic", "同步消息");
        return "发送成功，MsgId:" + result.getMsgId();
    }
}