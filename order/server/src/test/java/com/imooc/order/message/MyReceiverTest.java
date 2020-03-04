package com.imooc.order.message;

import com.imooc.order.OrderApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;

class MyReceiverTest extends OrderApplicationTests {

  @Autowired
  private AmqpTemplate amqpTemplate;

  @Test
  void process() {
    amqpTemplate.convertAndSend("zuoyuQueue", java.time.LocalDateTime.now().toString());
  }

  @Test
  void processBinding() {
    amqpTemplate.convertAndSend("zuoyuBindingQueue", java.time.LocalDateTime.now().toString());
  }

  @Test
  void processBindingComputer() {
    amqpTemplate
        .convertAndSend("zuoyuBindingExchange", "computer", "zuoyuBindingQueue",
            message -> new Message(
                java.time.LocalDateTime.now().toString().getBytes(),
                new MessageProperties()));
  }

  @Test
  void processBindingFruit() {
    amqpTemplate
        .convertAndSend("zuoyuBindingExchange", "fruit", "zuoyuBindingQueue",
            message -> new Message(
                java.time.LocalDateTime.now().toString().getBytes(),
                new MessageProperties()));
  }
}