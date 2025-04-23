package com.hongda.springbootlyb.pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description供应商考评信息表
 * @Author lyb
 * @Date 2025/4/23 10:25
 */
@Entity
@Table(name = "T_SUPPLIEREVALUATION")
public class SupplierEvaluation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer SUPPLIEREVALUATION_ID;
  private Integer OWNERUNIT_ID;
  private String OWNERUNIT_NAME;
  private Integer PROVINCE_CODE;
  private Integer SUPPLIER_ID;
  private Date EVALUATION_DATE;
  private BigDecimal EVALUATION_SCORE;
  private Integer EVALUATION_DEGREE;
  private String STATISTICS_TYPE;
  private Integer SUPPLIEREVALUATION_STATE;
  private Integer STAFF_ID;
  private String STAFF_NAME;
  private Date OPERATE_DATE;
  private String SUPPLIEREVALUATION_DESC;
  private BigDecimal TOTAL_SCORE;

  public Integer getSUPPLIEREVALUATION_ID() {
    return SUPPLIEREVALUATION_ID;
  }

  public void setSUPPLIEREVALUATION_ID(Integer SUPPLIEREVALUATION_ID) {
    this.SUPPLIEREVALUATION_ID = SUPPLIEREVALUATION_ID;
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

  public Integer getSUPPLIER_ID() {
    return SUPPLIER_ID;
  }

  public void setSUPPLIER_ID(Integer SUPPLIER_ID) {
    this.SUPPLIER_ID = SUPPLIER_ID;
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

  public String getSTATISTICS_TYPE() {
    return STATISTICS_TYPE;
  }

  public void setSTATISTICS_TYPE(String STATISTICS_TYPE) {
    this.STATISTICS_TYPE = STATISTICS_TYPE;
  }

  public Integer getSUPPLIEREVALUATION_STATE() {
    return SUPPLIEREVALUATION_STATE;
  }

  public void setSUPPLIEREVALUATION_STATE(Integer SUPPLIEREVALUATION_STATE) {
    this.SUPPLIEREVALUATION_STATE = SUPPLIEREVALUATION_STATE;
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

  public String getSUPPLIEREVALUATION_DESC() {
    return SUPPLIEREVALUATION_DESC;
  }

  public void setSUPPLIEREVALUATION_DESC(String SUPPLIEREVALUATION_DESC) {
    this.SUPPLIEREVALUATION_DESC = SUPPLIEREVALUATION_DESC;
  }

  public BigDecimal getTOTAL_SCORE() {
    return TOTAL_SCORE;
  }

  public void setTOTAL_SCORE(BigDecimal TOTAL_SCORE) {
    this.TOTAL_SCORE = TOTAL_SCORE;
  }
}
