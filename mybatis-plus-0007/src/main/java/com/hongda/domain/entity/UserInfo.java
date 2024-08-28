package com.hongda.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description
 * @Author lyb
 * @Date 2024/8/27 10:23
 */
@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class UserInfo {
  private Integer age;

  private String intro;

  private String gender;

}
