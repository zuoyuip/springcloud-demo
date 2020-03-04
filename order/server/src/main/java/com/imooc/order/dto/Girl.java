package com.imooc.order.dto;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * 演示自动刷新.
 *
 * @author zuoyu
 * @program order
 * @create 2020-02-29 15:17
 **/
@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "girl")
public class Girl {

  private String name;

  private String age;
}
