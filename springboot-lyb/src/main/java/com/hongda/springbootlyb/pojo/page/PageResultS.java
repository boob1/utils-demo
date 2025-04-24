package com.hongda.springbootlyb.pojo.page;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;

/**
 * @Description
 * @Author lyb
 * @Date 2025/4/23 11:58
 */
@JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
public class PageResultS<T> {

  private List<T> list;


  private Long TotalCount;


  private int PageSize;


  private int PageIndex;


  public List<T> getList() {
    return list;
  }

  public void setList(List<T> list) {
    this.list = list;
  }

  public Long getTotalCount() {
    return TotalCount;
  }

  public void setTotalCount(Long totalCount) {
    TotalCount = totalCount;
  }

  public int getPageSize() {
    return PageSize;
  }

  public void setPageSize(int pageSize) {
    PageSize = pageSize;
  }

  public int getPageIndex() {
    return PageIndex;
  }

  public void setPageIndex(int pageIndex) {
    PageIndex = pageIndex;
  }
}
