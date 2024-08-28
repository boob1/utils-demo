package com.hongda.domain.query;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description
 * @Author lyb
 * @Date 2024/8/27 17:53
 */
@ApiModel(description = "分页查询条件")
@Data
public class PageQuery {

  @ApiModelProperty("页码")
  private Long pageNo;

  @ApiModelProperty("每页数量")
  private Long pageSize;

  @ApiModelProperty("排序字段")
  private String sortBy;

  @ApiModelProperty("是否升序")
  private Boolean isAsc;


  public <T> Page<T> toMapPage(OrderItem... items) {
    Page<T> page = Page.of(pageNo, pageSize);
    if (StrUtil.isNotBlank(sortBy)) {
      page.addOrder(new OrderItem(sortBy, isAsc));
    } else {
      page.addOrder(items);
    }
    return page;
  }
  public <T>  Page<T> toMapPage(String defaultSortBy,Boolean defaultAsc){
    return toMapPage(new OrderItem(defaultSortBy,defaultAsc));
  }
  public <T>  Page<T> toMapPageDefaultSortByCreateTime(){
    return toMapPage(new OrderItem("create_time",false));
  }
  public <T>  Page<T> toMapPageDefaultSortByUpdateTime(){
    return toMapPage(new OrderItem("update_time",false));
  }
}
