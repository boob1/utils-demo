package com.hongda.rocketconsumer0014.listener;

import com.hongda.rocketconsumer0014.entity.Order;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RocketMQMessageListener(
        topic = "complex-topic",
        consumerGroup = "complex-consumer-group"
)
public class ComplexConsumer implements RocketMQListener<List<Order>> {
    @Override
    public void onMessage(List<Order> orders) {
        orders.forEach(order -> {
            System.out.println("订单ID: " + order.getOrderId());
            order.getProducts().forEach(p ->
                    System.out.println("  商品: " + p.getName()));
        });
    }
}