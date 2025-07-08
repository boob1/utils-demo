package com.hongda.rocketconsumer0014.listener;

import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;

// 消费者需实现顺序消费
@RocketMQMessageListener(
        topic = "order3-topic",
        consumerGroup = "order-group",
        consumeMode = ConsumeMode.ORDERLY  // 关键配置
)
public class OrderConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        // 获取当前线程信息（用于验证顺序性）
        String threadName = Thread.currentThread().getName();

        System.out.printf("[%s] 收到顺序消息：%s%n", threadName, message);

        // 模拟业务处理
        try {
            // 提取订单ID和步骤信息
            String orderId = message.substring(3, message.indexOf("]"));

            System.out.printf("[订单:%s] 处理步骤%d", orderId);
            Thread.sleep(500);  // 模拟处理耗时

            System.out.printf("[订单:%s] 步骤%d处理完成", orderId);
        } catch (Exception e) {
            e.printStackTrace();
            // 抛出异常会导致消息重试（顺序消费模式下会自动重试）
            throw new RuntimeException("消息处理失败", e);
        }
    }
}
