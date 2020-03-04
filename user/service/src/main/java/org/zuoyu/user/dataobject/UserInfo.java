package org.zuoyu.user.dataobject;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

/**
 * .
 *
 * @author zuoyu
 * @program user
 * @create 2020-03-03 22:01
 **/
@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "user_info", schema = "SpringCloud_Sell")
public class UserInfo {

  @Id
  private String id;
  private String username;
  private String password;
  private String openid;
  private byte role;
  private Timestamp createTime;
  private Timestamp updateTime;

}
