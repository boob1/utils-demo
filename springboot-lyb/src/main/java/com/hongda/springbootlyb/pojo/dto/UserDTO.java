package com.hongda.springbootlyb.pojo.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

/**
 * @Description
 * @Author lyb
 * @Date 2025/4/13 21:18
 */
public class UserDTO {

 @NotBlank(message = "用户名不能为空！")
  private String userName;

  @NotBlank(message = "密码不能为空！")
  @Length(min = 6,max = 20,message = "密码长度在6-20位之间！")
  private String userPassword;

  @NotBlank(message = "email格式不能为空！")
  private String email;


  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserPassword() {
    return userPassword;
  }

  public void setUserPassword(String userPassword) {
    this.userPassword = userPassword;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
