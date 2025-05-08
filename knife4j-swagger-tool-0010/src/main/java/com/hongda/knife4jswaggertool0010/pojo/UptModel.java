package com.hongda.knife4jswaggertool0010.pojo;


import io.swagger.annotations.ApiModelProperty;

/**
 * @Description
 * @Author lyb
 * @Date 2025/5/8 17:07
 */

public class UptModel {
  @ApiModelProperty(hidden = true)
  private String id;

  @ApiModelProperty(value="姓名")
  private String name;

  @ApiModelProperty(value="邮箱")
  private String email;

  @ApiModelProperty(value="订单信息")
  private OrderDate orderDate;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public OrderDate getOrderDate() {
    return orderDate;
  }

  public void setOrderDate(OrderDate orderDate) {
    this.orderDate = orderDate;
  }
}
