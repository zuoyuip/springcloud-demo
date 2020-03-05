package com.imooc.order.controller;

import com.imooc.order.exception.OrderException;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import java.util.Collections;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Hystrix测试.
 *
 * @author zuoyu
 * @program SpringCloud
 * @create 2020-03-05 14:37
 **/
@RestController
@RequestMapping(path = "/hystrix")
@DefaultProperties(defaultFallback = "defaultFallbackForgetProductInfoList", ignoreExceptions = {
    OrderException.class})
public class HystrixController {

  private final RestTemplate restTemplate;

  public HystrixController(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

//   超时设置
//  @HystrixCommand(commandProperties = {
//      @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
//  })

  //  断路器（设置熔断）
  @HystrixCommand(commandProperties = {
      @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
      @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
      @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
      @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")
  })
//  @HystrixCommand
  @GetMapping(path = "/getProductInfoList")
  public String getProductInfoList(@RequestParam("number") Integer number) {
    if (number % 2 == 0) {
      return "success";
    }
    return restTemplate.postForObject("http://PRODUCT/product/listForOrder",
        Collections.singletonList("164103465734242707"), String.class);
//    throw new OrderException(ResultEnum.ORDER_STATUS_ERROR);
  }

  private String fallbackForgetProductInfoList() {
    return "太拥挤，请稍候再试";
  }

  private String defaultFallbackForgetProductInfoList() {
    return "默认提示：太拥挤，请稍候再试";
  }
}
