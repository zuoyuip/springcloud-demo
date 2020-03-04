package com.imooc.order.controller;

import com.imooc.order.form.OrderForm;
import com.imooc.order.message.StreamClient;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 流检测.
 *
 * @author zuoyu
 * @program order
 * @create 2020-03-01 15:34
 **/
@RestController
public class StreamController {

  private final StreamClient streamClient;

  public StreamController(StreamClient streamClient) {
    this.streamClient = streamClient;
  }

  @PostMapping("/sendStream")
  public void processPost(OrderForm orderForm){
    streamClient.output().send(MessageBuilder.withPayload(orderForm).build());
  }

  @GetMapping("/sendStream")
  public void processGet(){
    streamClient.output().send(MessageBuilder.withPayload(new OrderForm()).build());
  }
}
