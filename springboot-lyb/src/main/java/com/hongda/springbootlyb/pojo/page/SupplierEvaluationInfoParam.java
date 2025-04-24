package com.hongda.springbootlyb.pojo.page;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Description
 * @Author lyb
 * @Date 2025/4/24 18:07
 */
@Data
public class SupplierEvaluationInfoParam {
  @JsonProperty("SUPPLIEREVALUATION_ID")
  private Integer SUPPLIEREVALUATION_ID;

  @JsonProperty("USER_ID")
  private Integer USER_ID;
}
