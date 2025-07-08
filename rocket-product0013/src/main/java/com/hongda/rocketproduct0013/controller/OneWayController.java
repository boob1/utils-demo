package com.hongda.rocketproduct0013.controller;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @Description:7.单向消息（只发送不等待）
 * 特点：吞吐量最高，但不保证可靠性
 */
@RestController
public class OneWayController {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("/oneway")
    public String sendOneWay() {
        rocketMQTemplate.sendOneWay("oneway-topic", "单向消息");
        return "单向消息已发出";
    }

}
