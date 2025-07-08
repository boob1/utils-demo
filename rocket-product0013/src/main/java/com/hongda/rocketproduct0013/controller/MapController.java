package com.hongda.rocketproduct0013.controller;

import jakarta.annotation.Resource;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
/**
 * @Description:3.发送map集合数据
 */
@RestController
public class MapController {
    @Resource
    private RocketMQTemplate rocketMQTemplate;
    @GetMapping("/send-map")
    public String sendMap() {
        Map<String, Integer> scores = new HashMap<>();
        scores.put("math", 90);
        scores.put("english", 85);
        rocketMQTemplate.convertAndSend("map-topic", scores);
        return "Map已发送";
    }

}
