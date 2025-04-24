package com.hongda.springbootlyb.pojo.page;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Description
 * @Author lyb
 * @Date 2025/4/23 23:31
 */
@Data
public class EvaluationRulesParam {
  @JsonProperty("EVALUATIONRULES_STATE")
private Integer EVALUATIONRULES_STATE;

  @JsonProperty("EVALUATION_DEPARTMENT_IDS")
private String EVALUATION_DEPARTMENT_IDS;
  @JsonProperty("OWNERUNIT_ID")
  private Integer OWNERUNIT_ID;

  @JsonProperty("PROVINCE_CODE")
  private Integer PROVINCE_CODE;

  @JsonProperty("USER_ID")
  private Integer USER_ID;




}
