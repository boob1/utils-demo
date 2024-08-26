package com.hongda.domain.vo;

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

  private String info;

  private Integer status;

  private String balance;

  private Date createTime;

  private Integer deleted;


}
