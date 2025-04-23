package com.hongda.springbootlyb.pojo.page;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Description
 * @Author lyb
 * @Date 2025/4/23 13:35
 */
public class PagePara {
  @JsonProperty("PageSize")
  private int PageSize;
  @JsonProperty("PageIndex")
  private int PageIndex;
  @JsonProperty("SearchParameter")
  private SearchParameter SearchParameter;

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

  public com.hongda.springbootlyb.pojo.page.SearchParameter getSearchParameter() {
    return SearchParameter;
  }

  public void setSearchParameter(
      com.hongda.springbootlyb.pojo.page.SearchParameter searchParameter) {
    SearchParameter = searchParameter;
  }
}
