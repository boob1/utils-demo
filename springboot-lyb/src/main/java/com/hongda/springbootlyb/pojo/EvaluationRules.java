package com.hongda.springbootlyb.pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description 考核规则表
 * @Author lyb
 * @Date 2025/4/23 10:21
 */
@Entity
@Table(name = "T_EVALUATIONRULES")
public class EvaluationRules {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
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

  public Integer getEVALUATIONRULES_ID() {
    return EVALUATIONRULES_ID;
  }

  public void setEVALUATIONRULES_ID(Integer EVALUATIONRULES_ID) {
    this.EVALUATIONRULES_ID = EVALUATIONRULES_ID;
  }

  public Integer getEVALUATIONRULES_PID() {
    return EVALUATIONRULES_PID;
  }

  public void setEVALUATIONRULES_PID(Integer EVALUATIONRULES_PID) {
    this.EVALUATIONRULES_PID = EVALUATIONRULES_PID;
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

  public Integer getEVALUATIONRULES_TYPE() {
    return EVALUATIONRULES_TYPE;
  }

  public void setEVALUATIONRULES_TYPE(Integer EVALUATIONRULES_TYPE) {
    this.EVALUATIONRULES_TYPE = EVALUATIONRULES_TYPE;
  }

  public Integer getEVALUATION_DEPARTMENT_ID() {
    return EVALUATION_DEPARTMENT_ID;
  }

  public void setEVALUATION_DEPARTMENT_ID(Integer EVALUATION_DEPARTMENT_ID) {
    this.EVALUATION_DEPARTMENT_ID = EVALUATION_DEPARTMENT_ID;
  }

  public String getEVALUATION_DEPARTMENT() {
    return EVALUATION_DEPARTMENT;
  }

  public void setEVALUATION_DEPARTMENT(String EVALUATION_DEPARTMENT) {
    this.EVALUATION_DEPARTMENT = EVALUATION_DEPARTMENT;
  }

  public Integer getEVALUATION_ITEMS() {
    return EVALUATION_ITEMS;
  }

  public void setEVALUATION_ITEMS(Integer EVALUATION_ITEMS) {
    this.EVALUATION_ITEMS = EVALUATION_ITEMS;
  }

  public String getEVALUATIONRULES_CONTENT() {
    return EVALUATIONRULES_CONTENT;
  }

  public void setEVALUATIONRULES_CONTENT(String EVALUATIONRULES_CONTENT) {
    this.EVALUATIONRULES_CONTENT = EVALUATIONRULES_CONTENT;
  }

  public BigDecimal getSTANDARD_SCORE() {
    return STANDARD_SCORE;
  }

  public void setSTANDARD_SCORE(BigDecimal STANDARD_SCORE) {
    this.STANDARD_SCORE = STANDARD_SCORE;
  }

  public BigDecimal getEVALUATION_RATIO() {
    return EVALUATION_RATIO;
  }

  public void setEVALUATION_RATIO(BigDecimal EVALUATION_RATIO) {
    this.EVALUATION_RATIO = EVALUATION_RATIO;
  }

  public Integer getEVALUATIONRULES_INDEX() {
    return EVALUATIONRULES_INDEX;
  }

  public void setEVALUATIONRULES_INDEX(Integer EVALUATIONRULES_INDEX) {
    this.EVALUATIONRULES_INDEX = EVALUATIONRULES_INDEX;
  }

  public Integer getEVALUATIONRULES_STATE() {
    return EVALUATIONRULES_STATE;
  }

  public void setEVALUATIONRULES_STATE(Integer EVALUATIONRULES_STATE) {
    this.EVALUATIONRULES_STATE = EVALUATIONRULES_STATE;
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

  public String getEVALUATIONRULES_DESC() {
    return EVALUATIONRULES_DESC;
  }

  public void setEVALUATIONRULES_DESC(String EVALUATIONRULES_DESC) {
    this.EVALUATIONRULES_DESC = EVALUATIONRULES_DESC;
  }
}
