package com.imooc.order.message;

import com.imooc.order.form.OrderForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.SendTo;

/**
 * 消息接收.
 *
 * @author zuoyu
 * @program order
 * @create 2020-03-01 15:19
 **/
@Slf4j
@EnableBinding(value = StreamClient.class)
public class StreamReceiver {

  @StreamListener(value = StreamClient.STREAM_NAME)
  @SendTo(value = StreamClient.RECEIVED_NAME)
  public String process(Message<OrderForm> orderFormMessage){
    log.info("process:{}", orderFormMessage.getPayload());
    return "received=" + orderFormMessage.getHeaders().getId();
  }

  @StreamListener(value = StreamClient.RECEIVED_NAME)
  public void processReceived(Message<String> message){
    log.info("processReceived:{}", message.getPayload());
  }
}
