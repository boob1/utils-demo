package com.hongda.springbootlyb.controller;

import com.hongda.springbootlyb.pojo.page.ResponseMessage;
import com.hongda.springbootlyb.pojo.User;
import com.hongda.springbootlyb.pojo.dto.UserDTO;
import com.hongda.springbootlyb.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author lyb
 * @Date 2025/4/7 14:53
 */
@RestController
@RequestMapping("/user")
public class UserController {

@Autowired
private IUserService userService;


  // 新增
  @PostMapping
  public ResponseMessage addUser(@Validated @RequestBody UserDTO userDTO){
    User user = userService.addUser(userDTO);
    return ResponseMessage.success(user,"成功");
  }

}
