package com.imooc.order.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * 流客户端.
 *
 * @author zuoyu
 * @program order
 * @create 2020-03-01 15:09
 **/
public interface StreamClient {

  String STREAM_NAME = "zuoyuStream";

  String RECEIVED_NAME = "zuoyuReceived";

  /**
   * 消息放入
   *
   * @return SubscribableChannel
   */
  @Input(value = STREAM_NAME)
  SubscribableChannel input();

  /**
   * 消息输出
   *
   * @return MessageChannel
   */
  @Output(value = STREAM_NAME)
  MessageChannel output();

  /**
   * 消息反馈放入
   *
   * @return SubscribableChannel
   */
  @Input(value = RECEIVED_NAME)
  SubscribableChannel inputReceived();

  /**
   * 消息反馈输出
   *
   * @return MessageChannel
   */
  @Output(value = RECEIVED_NAME)
  MessageChannel outputReceived();
}
