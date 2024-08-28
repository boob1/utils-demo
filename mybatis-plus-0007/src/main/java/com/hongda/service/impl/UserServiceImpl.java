package com.hongda.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.Db;
import com.hongda.domain.dto.PageDTO;
import com.hongda.domain.entity.MyAddress;
import com.hongda.domain.entity.User;
import com.hongda.domain.enums.UserStatus;
import com.hongda.domain.query.UserQuery;
import com.hongda.domain.vo.UserVo;
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
    if (user == null|| UserStatus.FROZEN.getValue()==1){
      throw new RuntimeException("用户不存在");
    }

    List<MyAddress> list = Db.lambdaQuery(MyAddress.class).eq(MyAddress::getUserId, id).list();
    user.setAddressList(list);
    return user;
  }

  @Override
  public PageDTO<UserVo> queryPage(UserQuery userQuery) {
    String name = userQuery.getName();
    Integer statue = userQuery.getStatue();

    Page<User> page = userQuery.toMapPageDefaultSortByUpdateTime();

    Page<User> userPage = lambdaQuery()
        .like(name != null, User::getUsername, name)
        .eq(statue != null, User::getStatus, statue)
        .page(page);

    // return PageDTO.of(userPage, UserVo.class);
    return PageDTO.of(userPage,user -> BeanUtil.copyProperties(user,UserVo.class));
  }
}
