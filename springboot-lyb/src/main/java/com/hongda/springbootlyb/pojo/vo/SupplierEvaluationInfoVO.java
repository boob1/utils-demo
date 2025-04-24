package com.hongda.springbootlyb.pojo.vo;

import cn.hutool.core.date.DateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @Description
 * @Author lyb
 * @Date 2025/4/24 9:21
 */
@Data
public class SupplierEvaluationInfoVO {
  @JsonProperty("SUPPLIEREVALUATIONINFO_ID")
  private Integer SUPPLIEREVALUATIONINFO_ID;
  @JsonProperty("SUPPLIEREVALUATION_ID")
  private Integer SUPPLIEREVALUATION_ID;
  @JsonProperty("EVALUATIONRULES_ID")
  private Integer EVALUATIONRULES_ID;
  @JsonProperty("EVALUATION_DEPARTMENT")
  private String EVALUATION_DEPARTMENT;
  @JsonProperty("EVALUATION_ITEMS")
  private Integer EVALUATION_ITEMS;
  @JsonProperty("EVALUATIONRULES_CONTENT")
  private String EVALUATIONRULES_CONTENT;
  @JsonProperty("STANDARD_SCORE")
  private BigDecimal STANDARD_SCORE;
  @JsonProperty("EVALUATION_SCORE")
  private BigDecimal EVALUATION_SCORE;
  @JsonProperty("DEDUCT_SCORE")
  private BigDecimal DEDUCT_SCORE;
  @JsonProperty("EVALUATION_STAFF")
  private String EVALUATION_STAFF;
  @JsonProperty("DEDUCT_REASON")
  private String DEDUCT_REASON;
  @JsonProperty("STAFF_ID")
  private Integer STAFF_ID;
  @JsonProperty("STAFF_NAME")
  private String STAFF_NAME;
  @JsonProperty("OPERATE_DATE")
  private Date OPERATE_DATE;
  @JsonProperty("USER_ID")
  private Integer USER_ID;
  @JsonProperty("SUPPLIEREVALUATIONINFO_DESC")
  private String SUPPLIEREVALUATIONINFO_DESC;

}
