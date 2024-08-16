package com.hongda.conf;


import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.hongda.enums.SensitiveEnum;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
/*@JsonSerialize(using = SensitiveSerialize.class)*/
public @interface SensitiveWrapped{
  /**
   * 脱敏类型
   * @return
   */
  SensitiveEnum value();

}
