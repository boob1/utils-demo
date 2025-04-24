package com.hongda.springbootlyb.pojo.vo;

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
  private Integer EVALUATIONRULES_ID;
  private Integer EVALUATIONRULES_PID;
  private Integer OWNERUNIT_ID;
  private String OWNERUNIT_NAME;
  private Integer PROVINCE_CODE;
  private Integer EVALUATIONRULES_TYPE;
  private Integer EVALUATION_DEPARTMENT_ID;
  private String EVALUATION_DEPARTMENT;
  private Integer EVALUATION_ITEMS;
  private String EVALUATIONRULES_CONTENT;
  private BigDecimal STANDARD_SCORE;
  private BigDecimal EVALUATION_RATIO;
  private Integer EVALUATIONRULES_INDEX;
  private Integer EVALUATIONRULES_STATE;
  private Integer STAFF_ID;
  private String STAFF_NAME;
  private Date OPERATE_DATE;
  private String EVALUATIONRULES_DESC;

}
