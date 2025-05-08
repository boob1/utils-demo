package com.hongda.knife4jswaggertool0010.pojo;


import io.swagger.annotations.ApiModelProperty;

/**
 * @Description
 * @Author lyb
 * @Date 2025/5/8 17:10
 */

public class OrderDate {
  @ApiModelProperty(value="主键id")
  private String id;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
