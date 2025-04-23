package com.hongda.springbootlyb.pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;

/**
 * @Description 自定义统计归口表
 * @Author lyb
 * @Date 2025/4/23 10:29
 */
@Entity
@Table(name = "T_AUTOSTATISTICS")
public class AutoStatistics {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer AUTOSTATISTICS_ID;
  private Integer AUTOSTATISTICS_PID;
  private String AUTOSTATISTICS_NAME;
  private String AUTOSTATISTICS_VALUE;
  private Integer AUTOSTATISTICS_INDEX;
  private Integer AUTOSTATISTICS_TYPE;
  private Integer STATISTICS_TYPE;
  private String AUTOSTATISTICS_ICO;
  private Integer OWNERUNIT_ID;
  private String OWNERUNIT_NAME;
  private Integer PROVINCE_CODE;
  private Integer AUTOSTATISTICS_STATE;
  private Integer STAFF_ID;
  private String STAF_NAME;
  private Date OPERATE_DATE;
  private String AUTOSTATISTICS_DESC;

  public Integer getAUTOSTATISTICS_ID() {
    return AUTOSTATISTICS_ID;
  }

  public void setAUTOSTATISTICS_ID(Integer AUTOSTATISTICS_ID) {
    this.AUTOSTATISTICS_ID = AUTOSTATISTICS_ID;
  }

  public Integer getAUTOSTATISTICS_PID() {
    return AUTOSTATISTICS_PID;
  }

  public void setAUTOSTATISTICS_PID(Integer AUTOSTATISTICS_PID) {
    this.AUTOSTATISTICS_PID = AUTOSTATISTICS_PID;
  }

  public String getAUTOSTATISTICS_NAME() {
    return AUTOSTATISTICS_NAME;
  }

  public void setAUTOSTATISTICS_NAME(String AUTOSTATISTICS_NAME) {
    this.AUTOSTATISTICS_NAME = AUTOSTATISTICS_NAME;
  }

  public String getAUTOSTATISTICS_VALUE() {
    return AUTOSTATISTICS_VALUE;
  }

  public void setAUTOSTATISTICS_VALUE(String AUTOSTATISTICS_VALUE) {
    this.AUTOSTATISTICS_VALUE = AUTOSTATISTICS_VALUE;
  }

  public Integer getAUTOSTATISTICS_INDEX() {
    return AUTOSTATISTICS_INDEX;
  }

  public void setAUTOSTATISTICS_INDEX(Integer AUTOSTATISTICS_INDEX) {
    this.AUTOSTATISTICS_INDEX = AUTOSTATISTICS_INDEX;
  }

  public Integer getAUTOSTATISTICS_TYPE() {
    return AUTOSTATISTICS_TYPE;
  }

  public void setAUTOSTATISTICS_TYPE(Integer AUTOSTATISTICS_TYPE) {
    this.AUTOSTATISTICS_TYPE = AUTOSTATISTICS_TYPE;
  }

  public Integer getSTATISTICS_TYPE() {
    return STATISTICS_TYPE;
  }

  public void setSTATISTICS_TYPE(Integer STATISTICS_TYPE) {
    this.STATISTICS_TYPE = STATISTICS_TYPE;
  }

  public String getAUTOSTATISTICS_ICO() {
    return AUTOSTATISTICS_ICO;
  }

  public void setAUTOSTATISTICS_ICO(String AUTOSTATISTICS_ICO) {
    this.AUTOSTATISTICS_ICO = AUTOSTATISTICS_ICO;
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

  public Integer getAUTOSTATISTICS_STATE() {
    return AUTOSTATISTICS_STATE;
  }

  public void setAUTOSTATISTICS_STATE(Integer AUTOSTATISTICS_STATE) {
    this.AUTOSTATISTICS_STATE = AUTOSTATISTICS_STATE;
  }

  public Integer getSTAFF_ID() {
    return STAFF_ID;
  }

  public void setSTAFF_ID(Integer STAFF_ID) {
    this.STAFF_ID = STAFF_ID;
  }

  public String getSTAF_NAME() {
    return STAF_NAME;
  }

  public void setSTAF_NAME(String STAF_NAME) {
    this.STAF_NAME = STAF_NAME;
  }

  public Date getOPERATE_DATE() {
    return OPERATE_DATE;
  }

  public void setOPERATE_DATE(Date OPERATE_DATE) {
    this.OPERATE_DATE = OPERATE_DATE;
  }

  public String getAUTOSTATISTICS_DESC() {
    return AUTOSTATISTICS_DESC;
  }

  public void setAUTOSTATISTICS_DESC(String AUTOSTATISTICS_DESC) {
    this.AUTOSTATISTICS_DESC = AUTOSTATISTICS_DESC;
  }
}
