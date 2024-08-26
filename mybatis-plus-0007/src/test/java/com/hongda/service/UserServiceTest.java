package com.hongda.service;

import com.hongda.domain.entity.User;
import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest{
  @Autowired
  UserService userService;
  @Test
  public void testGetById() {
    User user = new User();
    user.setUsername("张三1");
    user.setPassword("1234562");
    user.setPhone("123456789012");
    user.setCreateTime(new Date());
    user.setBalance("333344");
    user.setPhone("{2}");
    userService.save(user);
  }

}