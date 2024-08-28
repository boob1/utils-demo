package com.hongda.domain.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Description
 * @Author lyb
 * @Date 2024/8/27 17:58
 */
@Data
@ApiModel("用户查询条件")
@EqualsAndHashCode(callSuper = true)
public class UserQuery extends PageQuery{

  @ApiModelProperty("用户名")
  private String name;

  @ApiModelProperty("用户状态：1-正常；2-冻结")
  private Integer statue;

  @ApiModelProperty("用户余额")
  private Integer balance;

  @ApiModelProperty("用户余额最小值")
  private Integer minBalance;

  @ApiModelProperty("用户余额最大值")
  private Integer maxBalance;

}
