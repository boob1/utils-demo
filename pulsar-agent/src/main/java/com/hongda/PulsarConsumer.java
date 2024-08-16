package com.hongda;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Value;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.Schema;
import org.apache.pulsar.client.api.SubscriptionType;
import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @Author lyb
 * @Date 2024/8/16 16:25
 */
@Component
public class PulsarConsumer {
  @Value("${pulsar.service-url}")
  private String serviceUrl;

  @Value("${pulsar.topic}")
  private String topic;

  private PulsarClient pulsarClient;
  private Consumer<String> consumer;

  @PostConstruct
  public void init() throws PulsarClientException {
    pulsarClient = PulsarClient.builder()
        .serviceUrl(serviceUrl)
        .build();

    consumer = pulsarClient.newConsumer(Schema.STRING)
        .topic(topic)
        .subscriptionName("my-subscription")
        .subscriptionType(SubscriptionType.Shared)
        .subscribe();

    consumer.receiveAsync().thenAccept(this::handleMessage).exceptionally(ex -> {
      ex.printStackTrace();
      return null;
    });
  }

  private void handleMessage(Message<String> msg) {
    System.out.println("Received: " + msg.getValue());
    // Acknowledge the message so that it is no longer marked for redelivery
    try {
      consumer.acknowledge(msg);
    } catch (PulsarClientException e) {
      e.printStackTrace();
    }
    // Continue receiving messages
    consumer.receiveAsync().thenAccept(this::handleMessage);
  }

}
