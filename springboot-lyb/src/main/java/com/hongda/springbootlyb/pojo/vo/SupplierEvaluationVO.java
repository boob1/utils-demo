package com.hongda.springbootlyb.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hongda.springbootlyb.pojo.SupplierEvaluationInfo;
import com.hongda.springbootlyb.pojo.dto.SupplierEvaluationInfoDTO;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.Data;

/**
 * @Description
 * @Author lyb
 * @Date 2025/4/24 0:07
 */
@Data
public class SupplierEvaluationVO {
  @JsonProperty("SUPPLIEREVALUATION_ID")
  private Integer SUPPLIEREVALUATION_ID;
  @JsonProperty("OWNERUNIT_ID")
  private Integer OWNERUNIT_ID;
  @JsonProperty("OWNERUNIT_NAME")
  private String OWNERUNIT_NAME;
  @JsonProperty("PROVINCE_CODE")
  private Integer PROVINCE_CODE;
  @JsonProperty("SUPPLIER_ID")
  private Integer SUPPLIER_ID;
  @JsonProperty("EVALUATION_DATE")
  private Date EVALUATION_DATE;
  @JsonProperty("EVALUATION_SCORE")
  private BigDecimal EVALUATION_SCORE;
  @JsonProperty("EVALUATION_DEGREE")
  private String EVALUATION_DEGREE;
  @JsonProperty("STATISTICS_TYPE")
  private String STATISTICS_TYPE;
  @JsonProperty("SUPPLIEREVALUATION_STATE")
  private Integer SUPPLIEREVALUATION_STATE;
  @JsonProperty("STAFF_ID")
  private Integer STAFF_ID;
  @JsonProperty("STAFF_NAME")
  private String STAFF_NAME;
  @JsonProperty("OPERATE_DATE")
  private Date OPERATE_DATE;
  @JsonProperty("SUPPLIEREVALUATION_DESC")
  private String SUPPLIEREVALUATION_DESC;
  @JsonProperty("TOTAL_SCORE")
  private BigDecimal TOTAL_SCORE;
  @JsonProperty("DetailList")
  private List<SupplierEvaluationInfo> DetailList;
}
