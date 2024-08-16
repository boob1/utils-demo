package com.hongda.entity;

import com.hongda.conf.SensitiveWrapped;
import com.hongda.enums.SensitiveEnum;
import lombok.Data;

/**
 * @Description TODO
 * @Author lyb
 * @Date 2024/8/16 13:32
 */
@Data
public class UserEntity {
  /**
   * 用户ID
   */
  private Long userId;

  /**
   * 用户姓名
   */
  private String name;

  /**
   * 手机号
   */
  @SensitiveWrapped(SensitiveEnum.MOBILE_PHONE)
  private String mobile;

  /**
   * 身份证号码
   */
  @SensitiveWrapped(SensitiveEnum.ID_CARD)
  private String idCard;

  /**
   * 年龄
   */
  private String sex;

  /**
   * 性别
   */
  private int age;

}
