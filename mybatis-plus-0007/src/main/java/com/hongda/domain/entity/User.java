package com.hongda.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import java.util.List;
import lombok.Data;

/**
 * @Description
 * @Author lyb
 * @Date 2024/8/24 23:09
 */
@TableName("user")
@Data
public class User {
  @TableId(value = "id",type= IdType.ASSIGN_ID)
  private Long id;

  private String username;

  private String password;

  private String phone;

  private String info;

  private Integer status;

  private String balance;

  private Date createTime;

  private Integer deleted;

  private List<MyAddress> addressList;




}
