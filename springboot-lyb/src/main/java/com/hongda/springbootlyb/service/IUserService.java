package com.hongda.springbootlyb.service;

import com.hongda.springbootlyb.pojo.User;
import com.hongda.springbootlyb.pojo.dto.UserDTO;

public interface IUserService {

  User addUser(UserDTO userDTO);
}
