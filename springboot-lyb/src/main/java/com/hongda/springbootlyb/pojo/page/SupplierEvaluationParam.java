package com.hongda.springbootlyb.pojo.page;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Description
 * @Author lyb
 * @Date 2025/4/24 0:15
 */
@Data
public class SupplierEvaluationParam {
  @JsonProperty("SUPPLIER_IDS")
  private String SUPPLIER_IDS;

}
