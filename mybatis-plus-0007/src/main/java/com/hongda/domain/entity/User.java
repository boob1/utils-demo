package com.hongda.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.hongda.domain.enums.UserStatus;
import java.util.Date;
import java.util.List;
import lombok.Data;

/**
 * @Description
 * @Author lyb
 * @Date 2024/8/24 23:09
 */
@TableName(value="user", autoResultMap = true)
@Data
public class User {
  @TableId(value = "id",type= IdType.ASSIGN_ID)
  private Long id;

  private String username;

  private String password;

  private String phone;

  @TableField(typeHandler = JacksonTypeHandler.class)
  private UserInfo info;

  private UserStatus status;

  private String balance;

  private Date createTime;

  private Integer deleted;

  @TableField(exist = false)
  private List<MyAddress> addressList;




}
