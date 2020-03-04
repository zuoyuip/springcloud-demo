package org.zuoyu.user.service;

import org.zuoyu.user.dataobject.UserInfo;

/**
 * 用户.
 *
 * @author zuoyu
 * @program user
 * @create 2020-03-03 23:16
 **/
public interface UserService {

  UserInfo findByOpenId(String openId);
}
