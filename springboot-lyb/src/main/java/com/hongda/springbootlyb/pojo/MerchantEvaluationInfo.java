package com.hongda.springbootlyb.pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description合作商户考评详情表
 * @Author lyb
 * @Date 2025/4/23 10:24
 */
@Entity
@Table(name = "T_MERCHANTEVALUATIONINFO")
public class MerchantEvaluationInfo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer MERCHANTEVALUATIONINFO_ID;
  private Integer MERCHANTEVALUATION_ID;
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
  private String MERCHANTEVALUATIONINFO_DESC;

  public Integer getMERCHANTEVALUATIONINFO_ID() {
    return MERCHANTEVALUATIONINFO_ID;
  }

  public void setMERCHANTEVALUATIONINFO_ID(Integer MERCHANTEVALUATIONINFO_ID) {
    this.MERCHANTEVALUATIONINFO_ID = MERCHANTEVALUATIONINFO_ID;
  }

  public Integer getMERCHANTEVALUATION_ID() {
    return MERCHANTEVALUATION_ID;
  }

  public void setMERCHANTEVALUATION_ID(Integer MERCHANTEVALUATION_ID) {
    this.MERCHANTEVALUATION_ID = MERCHANTEVALUATION_ID;
  }

  public Integer getEVALUATIONRULES_ID() {
    return EVALUATIONRULES_ID;
  }

  public void setEVALUATIONRULES_ID(Integer EVALUATIONRULES_ID) {
    this.EVALUATIONRULES_ID = EVALUATIONRULES_ID;
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

  public BigDecimal getEVALUATION_SCORE() {
    return EVALUATION_SCORE;
  }

  public void setEVALUATION_SCORE(BigDecimal EVALUATION_SCORE) {
    this.EVALUATION_SCORE = EVALUATION_SCORE;
  }

  public BigDecimal getDEDUCT_SCORE() {
    return DEDUCT_SCORE;
  }

  public void setDEDUCT_SCORE(BigDecimal DEDUCT_SCORE) {
    this.DEDUCT_SCORE = DEDUCT_SCORE;
  }

  public String getEVALUATION_STAFF() {
    return EVALUATION_STAFF;
  }

  public void setEVALUATION_STAFF(String EVALUATION_STAFF) {
    this.EVALUATION_STAFF = EVALUATION_STAFF;
  }

  public String getDEDUCT_REASON() {
    return DEDUCT_REASON;
  }

  public void setDEDUCT_REASON(String DEDUCT_REASON) {
    this.DEDUCT_REASON = DEDUCT_REASON;
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

  public String getMERCHANTEVALUATIONINFO_DESC() {
    return MERCHANTEVALUATIONINFO_DESC;
  }

  public void setMERCHANTEVALUATIONINFO_DESC(String MERCHANTEVALUATIONINFO_DESC) {
    this.MERCHANTEVALUATIONINFO_DESC = MERCHANTEVALUATIONINFO_DESC;
  }
}
