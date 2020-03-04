package com.imooc.order.message;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * RabbitMQ测试.
 *
 * @author zuoyu
 * @program order
 * @create 2020-03-01 02:38
 **/
@Slf4j
@Component
public class MyReceiver {

  /**
   * 单消息队列
   */
  @RabbitListener(queuesToDeclare = @Queue(value = "zuoyuQueue"))
  public void process(String message) {
    log.info("process:{}", message);
  }

  /**
   * 消息队列绑定到分发器
   */
  @RabbitListener(bindings = @QueueBinding(
      value = @Queue(value = "zuoyuBindingQueue"),
      exchange = @Exchange(value = "zuoyuBindingExchange")
  ))
  public void processBinding(String message) {
    log.info("processBinding:{}", message);
  }

  /**
   * 数码订单
   * 消息队列绑定到分发器并制定规则
   */
  @RabbitListener(bindings = @QueueBinding(
      value = @Queue(value = "zuoyuBindingComputerQueue"),
      exchange = @Exchange(value = "zuoyuBindingExchange"),
      key = "computer"
  ))
  public void processBindingComputer(String message) {
    log.info("processBindingComputer:{}", message);
  }

  /**
   * 水果订单
   * 消息队列绑定到分发器并制定规则
   */
  @RabbitListener(bindings = @QueueBinding(
      value = @Queue(value = "zuoyuBindingFruitQueue"),
      exchange = @Exchange(value = "zuoyuBindingExchange"),
      key = "fruit"
  ))
  public void processBindingFruit(Message message) {
    log.info("processBindingFruit:{}", new String(message.getBody(), StandardCharsets.UTF_8));
    log.info("processBindingFruitDetail:{}", message.getMessageProperties());
  }
}
