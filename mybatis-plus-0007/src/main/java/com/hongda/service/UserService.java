package com.hongda.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hongda.domain.entity.User;
import java.util.List;

public interface UserService extends IService<User> {

  User queryById(Long id);
}
