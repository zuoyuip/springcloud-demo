package com.imooc.order.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 自动检测（方式之一）配置.
 *
 * @author zuoyu
 * @program SpringCloud
 * @create 2020-03-05 14:46
 **/
@SpringBootConfiguration
public class LoadBalancedConfig {

  @Bean
  @LoadBalanced
  public RestTemplate restTemplate(){
    return new RestTemplate();
  }
}
