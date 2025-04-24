package com.hongda.springbootlyb.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @Description
 * @Author lyb
 * @Date 2025/4/23 13:48
 */
@Data
public class EvaluationLevelVO {
  @JsonProperty("EVALUATIONLEVEL_ID")
  private Integer EVALUATIONLEVEL_ID;
  @JsonProperty("OWNERUNIT_ID")
  private Integer OWNERUNIT_ID;
  @JsonProperty("OWNERUNIT_NAME")
  private String OWNERUNIT_NAME;
  @JsonProperty("PROVINCE_CODE")
  private Integer PROVINCE_CODE;
  @JsonProperty("EVALUATIONLEVEL_TYPE")
  private Integer EVALUATIONLEVEL_TYPE;
  @JsonProperty("SCORE_START")
  private BigDecimal SCORE_START;
  @JsonProperty("SCORE_END")
  private BigDecimal SCORE_END;
  @JsonProperty("EVALUATIONLEVEL_VALUE")
  private Integer EVALUATIONLEVEL_VALUE;
  @JsonProperty("EVALUATIONLEVEL_NAME")
  private String EVALUATIONLEVEL_NAME;
  @JsonProperty("EVALUATIONLEVEL_INDEX")
  private Integer EVALUATIONLEVEL_INDEX;
  @JsonProperty("EVALUATIONLEVEL_STATE")
  private Integer EVALUATIONLEVEL_STATE;
  @JsonProperty("STAFF_ID")
  private Integer STAFF_ID;
  @JsonProperty("STAFF_NAME")
  private String STAFF_NAME;
  @JsonProperty("OPERATE_DATE")
  private Date OPERATE_DATE;
  @JsonProperty("EVALUATIONLEVEL_DESC")
  private String EVALUATIONLEVEL_DESC;

}
