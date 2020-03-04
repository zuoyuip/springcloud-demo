package org.zuoyu.user.service.impl;

import org.springframework.stereotype.Service;
import org.zuoyu.user.dataobject.UserInfo;
import org.zuoyu.user.repository.UserInfoRepository;
import org.zuoyu.user.service.UserService;

/**
 * .
 *
 * @author zuoyu
 * @program user
 * @create 2020-03-03 23:17
 **/
@Service
public class UserServiceImpl implements UserService {

  private final UserInfoRepository userInfoRepository;

  public UserServiceImpl(UserInfoRepository userInfoRepository) {
    this.userInfoRepository = userInfoRepository;
  }

  @Override
  public UserInfo findByOpenId(String openId) {
    return userInfoRepository.findByOpenid(openId);
  }
}
