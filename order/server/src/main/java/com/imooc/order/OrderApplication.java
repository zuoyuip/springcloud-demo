package com.imooc.order;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @EnableEurekaClient
 * @EnableCircuitBreaker
 * @SpringBootApplication 这三个配置可以用@SpringCloudApplication代替
 */
@EnableHystrix
@EnableHystrixDashboard
@SpringCloudApplication
@EnableFeignClients(basePackages = "com.imooc.product.client")
@ComponentScan(basePackages = "com.imooc")
public class OrderApplication {

  public static void main(String[] args) {
    SpringApplication.run(OrderApplication.class, args);
  }
}
