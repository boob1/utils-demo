package com.hongda.springbootlyb.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @Description 考核规则
 * @Author lyb
 * @Date 2025/4/23 23:20
 */
@Data
public class EvaluationRulesVO {
  @JsonProperty("EVALUATIONRULES_ID")
  private Integer EVALUATIONRULES_ID;
  @JsonProperty("EVALUATIONRULES_PID")
  private Integer EVALUATIONRULES_PID;
  @JsonProperty("OWNERUNIT_ID")
  private Integer OWNERUNIT_ID;
  @JsonProperty("OWNERUNIT_NAME")
  private String OWNERUNIT_NAME;
  @JsonProperty("PROVINCE_CODE")
  private Integer PROVINCE_CODE;
  @JsonProperty("EVALUATIONRULES_TYPE")
  private Integer EVALUATIONRULES_TYPE;
  @JsonProperty("EVALUATION_DEPARTMENT_ID")
  private Integer EVALUATION_DEPARTMENT_ID;
  @JsonProperty("EVALUATION_DEPARTMENT")
  private String EVALUATION_DEPARTMENT;
  @JsonProperty("EVALUATION_ITEMS")
  private Integer EVALUATION_ITEMS;
  @JsonProperty("EVALUATIONRULES_CONTENT")
  private String EVALUATIONRULES_CONTENT;
  @JsonProperty("STANDARD_SCORE")
  private BigDecimal STANDARD_SCORE;
  @JsonProperty("EVALUATION_RATIO")
  private BigDecimal EVALUATION_RATIO;
  @JsonProperty("EVALUATIONRULES_INDEX")
  private Integer EVALUATIONRULES_INDEX;
  @JsonProperty("EVALUATIONRULES_STATE")
  private Integer EVALUATIONRULES_STATE;
  @JsonProperty("STAFF_ID")
  private Integer STAFF_ID;
  @JsonProperty("STAFF_NAME")
  private String STAFF_NAME;
  @JsonProperty("OPERATE_DATE")
  private Date OPERATE_DATE;

  @JsonProperty("USER_ID")
  private Integer USER_ID;
  @JsonProperty("EVALUATIONRULES_DESC")
  private String EVALUATIONRULES_DESC;

}
