package com.hongda.springbootlyb.pojo.page;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Description
 * @Author lyb
 * @Date 2025/4/23 23:30
 */
@Data
public class EvaluationRulesPage {
  @JsonProperty("PageSize")
  private int PageSize;
  @JsonProperty("PageIndex")
  private int PageIndex;
  @JsonProperty("SearchParameter")
  private EvaluationRulesParam SearchParameter;

}
