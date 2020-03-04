package org.zuoyu.user.controller;

import static org.zuoyu.user.constant.CookieConstant.EXPIRE;
import static org.zuoyu.user.constant.CookieConstant.OPENID;
import static org.zuoyu.user.constant.CookieConstant.TOKEN;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zuoyu.user.constant.RedisConstant;
import org.zuoyu.user.dataobject.UserInfo;
import org.zuoyu.user.enums.ResultEnum;
import org.zuoyu.user.enums.RoleEnum;
import org.zuoyu.user.service.UserService;
import org.zuoyu.user.utils.CookieUtil;
import org.zuoyu.user.utils.ResultVOUtil;
import org.zuoyu.user.vo.ResultVO;

/**
 * 登录控制.
 *
 * @author zuoyu
 * @program user
 * @create 2020-03-04 13:40
 **/
@RestController
@RequestMapping(path = "/login")
public class LoginController {


  private final UserService userService;
  private final StringRedisTemplate stringRedisTemplate;

  public LoginController(UserService userService,
      StringRedisTemplate stringRedisTemplate) {
    this.userService = userService;
    this.stringRedisTemplate = stringRedisTemplate;
  }

  @GetMapping(path = "/buyer")
  public ResultVO buyer(@RequestParam(value = "openId") String openId,
      HttpServletResponse response) {
    UserInfo userInfo = userService.findByOpenId(openId);
    if (userInfo == null) {
      return ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
    }
    if (RoleEnum.BUYER.getCode() != userInfo.getRole()) {
      return ResultVOUtil.error(ResultEnum.ROLE_ERROR);
    }
    CookieUtil.set(response, OPENID, userInfo.getOpenid(), EXPIRE);
    return ResultVOUtil.success();
  }

  @GetMapping(path = "/seller")
  public ResultVO seller(@RequestParam(value = "openId") String openId,
      HttpServletRequest request, HttpServletResponse response) {
    Cookie cookie = CookieUtil.get(request, TOKEN);
    if (cookie != null && BooleanUtils.isTrue(stringRedisTemplate
        .hasKey(String.format(RedisConstant.TOKEN_TEMPLATE, cookie.getValue())))) {
      return ResultVOUtil.success();
    }
    UserInfo userInfo = userService.findByOpenId(openId);
    if (userInfo == null) {
      return ResultVOUtil.error(ResultEnum.LOGIN_FAIL);
    }
    if (RoleEnum.SELLER.getCode() != userInfo.getRole()) {
      return ResultVOUtil.error(ResultEnum.ROLE_ERROR);
    }
//    先设置到redis
    String token = UUID.randomUUID().toString();
    stringRedisTemplate.opsForValue()
        .set(String.format(RedisConstant.TOKEN_TEMPLATE, token), openId, EXPIRE, TimeUnit.SECONDS);
//    再设置到cookie
    CookieUtil.set(response, TOKEN, token, EXPIRE);
    return ResultVOUtil.success();
  }
}
