package org.zuoyu.user.enums;

import lombok.Getter;

/**
 * 权限.
 *
 * @author zuoyu
 * @program user
 * @create 2020-03-04 15:11
 **/
@Getter
public enum RoleEnum {
  BUYER(1, "买家"),
  SELLER(2, "卖家");

  private Integer code;

  private String message;

  RoleEnum(Integer code, String message) {
    this.code = code;
    this.message = message;
  }
}
