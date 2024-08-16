package com.hongda;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.Schema;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @Author lyb
 * @Date 2024/8/16 16:24
 */
@Component
public class PulsarProducer {

  @Value("${pulsar.service-url}")
  private String serviceUrl;

  @Value("${pulsar.topic-name}")
  private String topicName;

  private PulsarClient client;
  private Producer<String> producer;

  @PostConstruct
  public void init() throws Exception {
    client = PulsarClient.builder()
        .serviceUrl(serviceUrl)
        .build();

    producer = client.newProducer(Schema.STRING)
        .topic(topicName)
        .create();
  }

  public void send(String message) throws Exception {
    producer.send(message);
  }

  @PreDestroy
  public void close() throws Exception {
    producer.close();
    client.close();
  }

}
