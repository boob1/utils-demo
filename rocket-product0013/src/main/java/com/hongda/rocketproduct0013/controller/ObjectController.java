package com.hongda.rocketproduct0013.controller;

import com.hongda.rocketproduct0013.entity.User;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * @Description:1.发送对象消息
 */
@RestController
public class ObjectController {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("/send-user")
    public String sendUser() {
        User user = new User(1, "张三");
        rocketMQTemplate.convertAndSend("user-topic", user);
        return "用户对象已发送";
    }
}
