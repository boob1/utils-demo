package com.hongda.springbootlyb.pojo.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description
 * @Author lyb
 * @Date 2025/4/23 13:48
 */

public class EvaluationLevelVO {
  private Integer EVALUATIONLEVEL_ID;
  private Integer OWNERUNIT_ID;
  private String OWNERUNIT_NAME;
  private Integer PROVINCE_CODE;
  private Integer EVALUATIONLEVEL_TYPE;
  private BigDecimal SCORE_START;
  private BigDecimal SCORE_END;
  private Integer EVALUATIONLEVEL_VALUE;
  private String EVALUATIONLEVEL_NAME;
  private Integer EVALUATIONLEVEL_INDEX;
  private Integer EVALUATIONLEVEL_STATE;
  private Integer STAFF_ID;
  private String STAFF_NAME;
  private Date OPERATE_DATE;
  private String EVALUATIONLEVEL_DESC;

  public Integer getEVALUATIONLEVEL_ID() {
    return EVALUATIONLEVEL_ID;
  }

  public void setEVALUATIONLEVEL_ID(Integer EVALUATIONLEVEL_ID) {
    this.EVALUATIONLEVEL_ID = EVALUATIONLEVEL_ID;
  }

  public Integer getOWNERUNIT_ID() {
    return OWNERUNIT_ID;
  }

  public void setOWNERUNIT_ID(Integer OWNERUNIT_ID) {
    this.OWNERUNIT_ID = OWNERUNIT_ID;
  }

  public String getOWNERUNIT_NAME() {
    return OWNERUNIT_NAME;
  }

  public void setOWNERUNIT_NAME(String OWNERUNIT_NAME) {
    this.OWNERUNIT_NAME = OWNERUNIT_NAME;
  }

  public Integer getPROVINCE_CODE() {
    return PROVINCE_CODE;
  }

  public void setPROVINCE_CODE(Integer PROVINCE_CODE) {
    this.PROVINCE_CODE = PROVINCE_CODE;
  }

  public Integer getEVALUATIONLEVEL_TYPE() {
    return EVALUATIONLEVEL_TYPE;
  }

  public void setEVALUATIONLEVEL_TYPE(Integer EVALUATIONLEVEL_TYPE) {
    this.EVALUATIONLEVEL_TYPE = EVALUATIONLEVEL_TYPE;
  }

  public BigDecimal getSCORE_START() {
    return SCORE_START;
  }

  public void setSCORE_START(BigDecimal SCORE_START) {
    this.SCORE_START = SCORE_START;
  }

  public BigDecimal getSCORE_END() {
    return SCORE_END;
  }

  public void setSCORE_END(BigDecimal SCORE_END) {
    this.SCORE_END = SCORE_END;
  }

  public Integer getEVALUATIONLEVEL_VALUE() {
    return EVALUATIONLEVEL_VALUE;
  }

  public void setEVALUATIONLEVEL_VALUE(Integer EVALUATIONLEVEL_VALUE) {
    this.EVALUATIONLEVEL_VALUE = EVALUATIONLEVEL_VALUE;
  }

  public String getEVALUATIONLEVEL_NAME() {
    return EVALUATIONLEVEL_NAME;
  }

  public void setEVALUATIONLEVEL_NAME(String EVALUATIONLEVEL_NAME) {
    this.EVALUATIONLEVEL_NAME = EVALUATIONLEVEL_NAME;
  }

  public Integer getEVALUATIONLEVEL_INDEX() {
    return EVALUATIONLEVEL_INDEX;
  }

  public void setEVALUATIONLEVEL_INDEX(Integer EVALUATIONLEVEL_INDEX) {
    this.EVALUATIONLEVEL_INDEX = EVALUATIONLEVEL_INDEX;
  }

  public Integer getEVALUATIONLEVEL_STATE() {
    return EVALUATIONLEVEL_STATE;
  }

  public void setEVALUATIONLEVEL_STATE(Integer EVALUATIONLEVEL_STATE) {
    this.EVALUATIONLEVEL_STATE = EVALUATIONLEVEL_STATE;
  }

  public Integer getSTAFF_ID() {
    return STAFF_ID;
  }

  public void setSTAFF_ID(Integer STAFF_ID) {
    this.STAFF_ID = STAFF_ID;
  }

  public String getSTAFF_NAME() {
    return STAFF_NAME;
  }

  public void setSTAFF_NAME(String STAFF_NAME) {
    this.STAFF_NAME = STAFF_NAME;
  }

  public Date getOPERATE_DATE() {
    return OPERATE_DATE;
  }

  public void setOPERATE_DATE(Date OPERATE_DATE) {
    this.OPERATE_DATE = OPERATE_DATE;
  }

  public String getEVALUATIONLEVEL_DESC() {
    return EVALUATIONLEVEL_DESC;
  }

  public void setEVALUATIONLEVEL_DESC(String EVALUATIONLEVEL_DESC) {
    this.EVALUATIONLEVEL_DESC = EVALUATIONLEVEL_DESC;
  }
}
