package com.hongda.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hongda.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}
