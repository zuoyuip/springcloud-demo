package com.imooc.order.message;

import com.imooc.order.OrderApplicationTests;
import java.time.LocalDateTime;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;

class StreamClientTest extends OrderApplicationTests {

  @Autowired
  private StreamClient streamClient;

  @Test
  void input() {
    boolean send = streamClient.input()
        .send(MessageBuilder.withPayload(LocalDateTime.now().toString()).build());
    Assert.assertTrue(send);
  }

  @Test
  void output() {
    boolean send = streamClient.output()
        .send(MessageBuilder.withPayload(LocalDateTime.now().toString()).build());
    Assert.assertTrue(send);
  }
}