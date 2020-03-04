package com.imooc.order.controller;

import com.imooc.order.dto.Girl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 演示自动刷新.
 *
 * @author zuoyu
 * @program order
 * @create 2020-02-29 15:19
 **/
@RestController
public class GirlController {

  private final Girl girl;

  public GirlController(Girl girl) {
    this.girl = girl;
  }

  @GetMapping(path = "/girl")
  public String println(){
    return girl.toString();
  }
}
