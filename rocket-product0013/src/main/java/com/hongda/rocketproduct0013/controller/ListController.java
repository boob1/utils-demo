package com.hongda.rocketproduct0013.controller;

import jakarta.annotation.Resource;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @Description:2.发送集合消息
 */
@RestController
public class ListController {
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("/send-list")
    public String sendList() {
        List<String> names = Arrays.asList("张三", "李四", "王五");
        rocketMQTemplate.convertAndSend("list-topic", names);
        return "列表已发送";
    }

}
