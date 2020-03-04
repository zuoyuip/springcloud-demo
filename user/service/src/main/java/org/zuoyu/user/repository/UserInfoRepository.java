package org.zuoyu.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.zuoyu.user.dataobject.UserInfo;

/**
 * 用户的数据库.
 *
 * @author zuoyu
 * @program user
 * @create 2020-03-03 22:08
 **/
@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, String>,
    JpaSpecificationExecutor<UserInfo> {


  UserInfo findByOpenid(String openId);
}
