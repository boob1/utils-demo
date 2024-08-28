package com.hongda.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongda.domain.dto.PageDTO;
import com.hongda.domain.dto.UserFormDTO;
import com.hongda.domain.entity.User;
import com.hongda.domain.query.UserQuery;
import com.hongda.domain.vo.UserVo;
import com.hongda.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author lyb
 * @Date 2024/8/25 22:22
 */
@Api(tags = "用户管理")
@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserController {
 private final UserService userService;
  @ApiOperation(value = "保存用户")
  @PostMapping
  public void saveUser(@RequestBody UserFormDTO userFormDTO){
    //DTO 转化PO
    User user = BeanUtil.copyProperties(userFormDTO, User.class);
    userService.save(user);
  }

  @DeleteMapping("{id}")
  @ApiOperation(value = "删除用户")
  public void delete(@ApiParam("用户id") @PathVariable("id") Long id){
    userService.removeById(id);
  }


  @GetMapping("{id}")
  @ApiOperation(value = "查询用户")
  public User findByIds(@ApiParam("用户id") @PathVariable("id") Long id){
    User users = userService.queryById(id);
    return users;
  }
  @GetMapping()
  @ApiOperation(value = "查询用户")
  public List<UserVo> findByIds(@ApiParam("用户id") @RequestParam("id") List<Long> id){
    List<User> users = userService.listByIds(id);
    return BeanUtil.copyToList(users, UserVo.class);
  }


  @GetMapping("/queryPage")
  @ApiOperation(value = "查询用户")
  public PageDTO<UserVo> queryPage(UserQuery userQuery){
    return userService.queryPage(userQuery);
  }


}
