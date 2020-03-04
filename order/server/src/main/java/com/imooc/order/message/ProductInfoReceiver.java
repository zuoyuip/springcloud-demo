package com.imooc.order.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.product.common.ProductInfoOutput;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 库存消息.
 *
 * @author zuoyu
 * @program order
 * @create 2020-03-02 12:38
 **/
@Slf4j
@Component
public class ProductInfoReceiver {

  private static final String PRODUCT_INFO_STOCK_TEMPLATE = "product_info_stock_%s";
  private final StringRedisTemplate stringRedisTemplate;

  public ProductInfoReceiver(
      StringRedisTemplate stringRedisTemplate) {
    this.stringRedisTemplate = stringRedisTemplate;
  }

  @Transactional(rollbackFor = Exception.class)
  @RabbitListener(queuesToDeclare = @Queue("productInfo"))
  public void process(String message) throws JsonProcessingException {
    List<ProductInfoOutput> productInfoOutputs = new ObjectMapper()
        .readValue(message, new TypeReference<List<ProductInfoOutput>>() {
        });
//    存储到redis
    productInfoOutputs.forEach(productInfoOutput -> {
      stringRedisTemplate.opsForValue()
          .set(String.format(PRODUCT_INFO_STOCK_TEMPLATE, productInfoOutput.getProductId()),
              productInfoOutput.getProductStock().toString());
    });
  }
}
