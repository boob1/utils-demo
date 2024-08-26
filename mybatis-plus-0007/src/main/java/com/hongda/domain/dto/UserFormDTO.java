package com.hongda.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description
 * @Author lyb
 * @Date 2024/8/25 22:14
 */
@Data
@ApiModel(description = "用户表单")
public class UserFormDTO {

  @ApiModelProperty(value = "用户id")
  private Long id;

 @ApiModelProperty(value = "用户名")
  private String username;


 @ApiModelProperty(value = "密码")
  private String password;
 @ApiModelProperty(value = "手机号")
  private String phone;

  @ApiModelProperty(value = "详细信息")
  private String info;
  @ApiModelProperty(value = "账户金额")
   private Integer balance;

}
