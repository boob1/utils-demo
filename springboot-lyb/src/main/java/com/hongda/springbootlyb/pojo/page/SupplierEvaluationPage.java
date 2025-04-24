package com.hongda.springbootlyb.pojo.page;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Description
 * @Author lyb
 * @Date 2025/4/24 0:15
 */
@Data
public class SupplierEvaluationPage {
  @JsonProperty("PageSize")
  private int PageSize;
  @JsonProperty("PageIndex")
  private int PageIndex;
  @JsonProperty("SearchParameter")
  private SupplierEvaluationParam SearchParameter;

}
