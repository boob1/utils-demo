package com.hongda.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hongda.domain.dto.PageDTO;
import com.hongda.domain.entity.User;
import com.hongda.domain.query.UserQuery;
import com.hongda.domain.vo.UserVo;

public interface UserService extends IService<User> {

  User queryById(Long id);

  PageDTO<UserVo> queryPage(UserQuery userQuery);
}
