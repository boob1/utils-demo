package com.hongda.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.hongda.domain.entity.MyAddress;
import com.hongda.domain.entity.User;
import com.hongda.mapper.UserMapper;
import com.hongda.service.UserService;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author lyb
 * @Date 2024/8/25 16:52
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

  @Override
  public User queryById(Long id) {
    User user = getById(id);
    if (user != null||user.getStatus()==2){
      throw new RuntimeException("用户不存在");
    }

    List<MyAddress> list = Db.lambdaQuery(MyAddress.class).eq(MyAddress::getUserId, id).list();
    user.setAddressList(list);
    return user;
  }
}
