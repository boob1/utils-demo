package com.hongda.domain.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum UserStatus {
  NORMAL(0, "正常"),
  FROZEN(1, "冻结");

  @EnumValue
  private final int value;


  @JsonValue
  private final String desc;

  private UserStatus(int value, String desc) {
    this.value = value;
    this.desc = desc;
  }
}
