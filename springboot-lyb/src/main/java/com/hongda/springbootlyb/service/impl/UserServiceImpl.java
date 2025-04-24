package com.hongda.springbootlyb.service.impl;

import com.hongda.springbootlyb.dao.UserRepository;
import com.hongda.springbootlyb.pojo.User;
import com.hongda.springbootlyb.pojo.dto.UserDTO;
import com.hongda.springbootlyb.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author lyb
 * @Date 2025/4/13 21:39
 */
@Service
public class UserServiceImpl implements IUserService {

  @Autowired
  UserRepository userRepository;
  @Override
  public User addUser(UserDTO userDTO) {
    User user = new User();
    BeanUtils.copyProperties(userDTO,user);
    return userRepository.save(user);
  }
}
