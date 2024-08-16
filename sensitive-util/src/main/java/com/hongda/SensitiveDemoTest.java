package com.hongda;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hongda.entity.UserEntity;

/**
 * @Description TODO
 * @Author lyb
 * @Date 2024/8/16 13:33
 */
public class SensitiveDemoTest {

  public static void main(String[] args) throws JsonProcessingException {
    UserEntity userEntity = new UserEntity();
    userEntity.setUserId(1l);
    userEntity.setName("张三");
    userEntity.setMobile("18000000001");
    userEntity.setIdCard("420117200001011000008888");
    userEntity.setAge(20);
    userEntity.setSex("男");

    //通过jackson方式，将对象序列化成json字符串
    ObjectMapper objectMapper = new ObjectMapper();
    System.out.println(objectMapper.writeValueAsString(userEntity));
  }

}
