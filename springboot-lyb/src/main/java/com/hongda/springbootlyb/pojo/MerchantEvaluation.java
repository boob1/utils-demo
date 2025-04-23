package com.hongda.springbootlyb.pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description 合作商户考评信息表
 * @Author lyb
 * @Date 2025/4/23 10:23
 */
@Entity
@Table(name = "T_MERCHANTEVALUATION")
public class MerchantEvaluation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer MERCHANTEVALUATION_ID;
  private Integer OWNERUNIT_ID;
  private String OWNERUNIT_NAME;
  private Integer PROVINCE_CODE;
  private Integer MERCHANTS_ID;
  private String STATISTICS_TYPE;
  private Integer REGION_ID;
  private String REGION_NAME;
  private Integer SERVERPART_ID;
  private String SERVERPART_NAME;
  private String BUSINESS_TRADE;
  private Integer SERVERPARTSHOP_ID;
  private String SHOPNAME;
  private String EVALUATION_DEPARTMENT;
  private Date EVALUATION_DATE;
  private BigDecimal EVALUATION_SCORE;
  private Integer EVALUATION_DEGREE;
  private Integer MERCHANTEVALUATION_STATE;
  private Integer STAFF_ID;
  private String STAFF_NAME;
  private Date OPERATE_DATE;
  private String MERCHANTEVALUATION_DESC;

  public Integer getMERCHANTEVALUATION_ID() {
    return MERCHANTEVALUATION_ID;
  }

  public void setMERCHANTEVALUATION_ID(Integer MERCHANTEVALUATION_ID) {
    this.MERCHANTEVALUATION_ID = MERCHANTEVALUATION_ID;
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

  public Integer getMERCHANTS_ID() {
    return MERCHANTS_ID;
  }

  public void setMERCHANTS_ID(Integer MERCHANTS_ID) {
    this.MERCHANTS_ID = MERCHANTS_ID;
  }

  public String getSTATISTICS_TYPE() {
    return STATISTICS_TYPE;
  }

  public void setSTATISTICS_TYPE(String STATISTICS_TYPE) {
    this.STATISTICS_TYPE = STATISTICS_TYPE;
  }

  public Integer getREGION_ID() {
    return REGION_ID;
  }

  public void setREGION_ID(Integer REGION_ID) {
    this.REGION_ID = REGION_ID;
  }

  public String getREGION_NAME() {
    return REGION_NAME;
  }

  public void setREGION_NAME(String REGION_NAME) {
    this.REGION_NAME = REGION_NAME;
  }

  public Integer getSERVERPART_ID() {
    return SERVERPART_ID;
  }

  public void setSERVERPART_ID(Integer SERVERPART_ID) {
    this.SERVERPART_ID = SERVERPART_ID;
  }

  public String getSERVERPART_NAME() {
    return SERVERPART_NAME;
  }

  public void setSERVERPART_NAME(String SERVERPART_NAME) {
    this.SERVERPART_NAME = SERVERPART_NAME;
  }

  public String getBUSINESS_TRADE() {
    return BUSINESS_TRADE;
  }

  public void setBUSINESS_TRADE(String BUSINESS_TRADE) {
    this.BUSINESS_TRADE = BUSINESS_TRADE;
  }

  public Integer getSERVERPARTSHOP_ID() {
    return SERVERPARTSHOP_ID;
  }

  public void setSERVERPARTSHOP_ID(Integer SERVERPARTSHOP_ID) {
    this.SERVERPARTSHOP_ID = SERVERPARTSHOP_ID;
  }

  public String getSHOPNAME() {
    return SHOPNAME;
  }

  public void setSHOPNAME(String SHOPNAME) {
    this.SHOPNAME = SHOPNAME;
  }

  public String getEVALUATION_DEPARTMENT() {
    return EVALUATION_DEPARTMENT;
  }

  public void setEVALUATION_DEPARTMENT(String EVALUATION_DEPARTMENT) {
    this.EVALUATION_DEPARTMENT = EVALUATION_DEPARTMENT;
  }

  public Date getEVALUATION_DATE() {
    return EVALUATION_DATE;
  }

  public void setEVALUATION_DATE(Date EVALUATION_DATE) {
    this.EVALUATION_DATE = EVALUATION_DATE;
  }

  public BigDecimal getEVALUATION_SCORE() {
    return EVALUATION_SCORE;
  }

  public void setEVALUATION_SCORE(BigDecimal EVALUATION_SCORE) {
    this.EVALUATION_SCORE = EVALUATION_SCORE;
  }

  public Integer getEVALUATION_DEGREE() {
    return EVALUATION_DEGREE;
  }

  public void setEVALUATION_DEGREE(Integer EVALUATION_DEGREE) {
    this.EVALUATION_DEGREE = EVALUATION_DEGREE;
  }

  public Integer getMERCHANTEVALUATION_STATE() {
    return MERCHANTEVALUATION_STATE;
  }

  public void setMERCHANTEVALUATION_STATE(Integer MERCHANTEVALUATION_STATE) {
    this.MERCHANTEVALUATION_STATE = MERCHANTEVALUATION_STATE;
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

  public String getMERCHANTEVALUATION_DESC() {
    return MERCHANTEVALUATION_DESC;
  }

  public void setMERCHANTEVALUATION_DESC(String MERCHANTEVALUATION_DESC) {
    this.MERCHANTEVALUATION_DESC = MERCHANTEVALUATION_DESC;
  }
}
