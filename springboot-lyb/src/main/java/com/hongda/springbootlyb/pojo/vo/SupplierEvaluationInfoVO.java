package com.hongda.springbootlyb.pojo.vo;

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
  private Integer SUPPLIEREVALUATIONINFO_ID;
  private Integer SUPPLIEREVALUATION_ID;
  private Integer EVALUATIONRULES_ID;
  private String EVALUATION_DEPARTMENT;
  private Integer EVALUATION_ITEMS;
  private String EVALUATIONRULES_CONTENT;
  private BigDecimal STANDARD_SCORE;
  private BigDecimal EVALUATION_SCORE;
  private BigDecimal DEDUCT_SCORE;
  private String EVALUATION_STAFF;
  private String DEDUCT_REASON;
  private Integer STAFF_ID;
  private String STAFF_NAME;
  private Date OPERATE_DATE;
  private String SUPPLIEREVALUATIONINFO_DESC;

}
