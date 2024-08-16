package com.hongda.controller;

import com.hongda.entity.UserEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description TODO
 * @Author lyb
 * @Date 2024/8/16 13:37
 */
@RestController
public class SensitiveController {
  @RequestMapping("/hello")
  public UserEntity hello() {
    UserEntity userEntity = new UserEntity();
    userEntity.setUserId(1l);
    userEntity.setName("张三");
    userEntity.setMobile("18000000001");
    userEntity.setIdCard("420117200001011000008888");
    userEntity.setAge(20);
    userEntity.setSex("男");
    return userEntity;
  }

}
