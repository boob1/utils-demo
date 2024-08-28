package com.hongda.domain.vo;

import com.hongda.domain.entity.UserInfo;
import com.hongda.domain.enums.UserStatus;
import java.util.Date;
import lombok.Data;

/**
 * @Description
 * @Author lyb
 * @Date 2024/8/25 22:42
 */
@Data
public class UserVo {
  private Long id;

  private String username;

  private String password;

  private String phone;

  private UserInfo info;

  private UserStatus status;

  private String balance;

  private Date createTime;

  private Integer deleted;


}
