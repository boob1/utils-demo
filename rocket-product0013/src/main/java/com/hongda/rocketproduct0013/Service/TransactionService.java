package com.hongda.rocketproduct0013.Service;

import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
@RocketMQTransactionListener
public class TransactionService implements RocketMQLocalTransactionListener {
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        try {
            String topic = msg.getHeaders().get("topic", String.class);

            if ("tx-topic".equals(topic)) {
                // 处理 TOPIC_A 的本地事务
            } else if ("TOPIC_B".equals(topic)) {
                // 处理 TOPIC_B 的本地事务
            }
            // 执行本地事务
            System.out.println("执行本地事务...");
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        // 事务状态检查（补偿机制）
        String topic = msg.getHeaders().get("topic", String.class);
        // 同上
        return RocketMQLocalTransactionState.COMMIT;
    }
}
