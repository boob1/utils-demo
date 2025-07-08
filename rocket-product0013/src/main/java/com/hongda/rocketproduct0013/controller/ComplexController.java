package com.hongda.rocketproduct0013.controller;

import com.hongda.rocketproduct0013.entity.Order;
import com.hongda.rocketproduct0013.entity.Product;
import jakarta.annotation.Resource;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @Description:4.
 */
@RestController
public class ComplexController {
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @GetMapping("/send-orders")
    public String sendOrders() {
        List<Order> orders = Arrays.asList(
                new Order(1001L, Arrays.asList(new Product("手机"), new Product("耳机"))),
                new Order(1002L, Arrays.asList(new Product("电脑")))
        );
        rocketMQTemplate.convertAndSend("complex-topic", orders);
        return "订单集合已发送";
    }
}
