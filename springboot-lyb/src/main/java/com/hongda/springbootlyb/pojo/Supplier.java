package com.hongda.springbootlyb.pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
import javax.annotation.processing.Generated;

/**
 * @Description 供货商
 * @Author lyb
 * @Date 2025/4/23 10:30
 */
@Entity
@Table(name = "T_SUPPLIER")
public class Supplier {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer SUPPLIER_ID;
  private Integer SUPPLIER_PID;
  private Integer OWNERUNIT_ID;
  private String OWNERUNIT_NAME;
  private Integer PROVINCE_CODE;
  private Integer SUPPLIER_NATURE;
  private String SUPPLIER_CODE;
  private String SUPPLIER_NAME;
  private String SUPPLIER_TYPE;
  private String SUPPLIER_EN;
  private String TAXPAYER_IDENTIFYCODE;
  private String SUPPLIER_DRAWER;
  private String BANK_NAME;
  private String BANK_ACCOUNT;
  private String SUPPLIER_LINKMAN;
  private String SUPPLIER_TELEPHONE;
  private String SUPPLIER_MOBILEPHONE;
  private String SUPPLIER_FAX;
  private String SUPPLIER_EMAIL;
  private String SUPPLIER_ADDRESS;
  private Integer SUPPLIER_STATE;
  private String RTWECHATPUSH_IDS;
  private Integer STAFF_ID;
  private String STAFF_NAME;
  private Date OPERATE_DATE;
  private String SUPPLIER_DESC;
  private Integer MERCHANTTYPE_ID;
  private String MERCHANTTYPE_NAME;

  public Integer getSUPPLIER_ID() {
    return SUPPLIER_ID;
  }

  public void setSUPPLIER_ID(Integer SUPPLIER_ID) {
    this.SUPPLIER_ID = SUPPLIER_ID;
  }

  public Integer getSUPPLIER_PID() {
    return SUPPLIER_PID;
  }

  public void setSUPPLIER_PID(Integer SUPPLIER_PID) {
    this.SUPPLIER_PID = SUPPLIER_PID;
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

  public Integer getSUPPLIER_NATURE() {
    return SUPPLIER_NATURE;
  }

  public void setSUPPLIER_NATURE(Integer SUPPLIER_NATURE) {
    this.SUPPLIER_NATURE = SUPPLIER_NATURE;
  }

  public String getSUPPLIER_CODE() {
    return SUPPLIER_CODE;
  }

  public void setSUPPLIER_CODE(String SUPPLIER_CODE) {
    this.SUPPLIER_CODE = SUPPLIER_CODE;
  }

  public String getSUPPLIER_NAME() {
    return SUPPLIER_NAME;
  }

  public void setSUPPLIER_NAME(String SUPPLIER_NAME) {
    this.SUPPLIER_NAME = SUPPLIER_NAME;
  }

  public String getSUPPLIER_TYPE() {
    return SUPPLIER_TYPE;
  }

  public void setSUPPLIER_TYPE(String SUPPLIER_TYPE) {
    this.SUPPLIER_TYPE = SUPPLIER_TYPE;
  }

  public String getSUPPLIER_EN() {
    return SUPPLIER_EN;
  }

  public void setSUPPLIER_EN(String SUPPLIER_EN) {
    this.SUPPLIER_EN = SUPPLIER_EN;
  }

  public String getTAXPAYER_IDENTIFYCODE() {
    return TAXPAYER_IDENTIFYCODE;
  }

  public void setTAXPAYER_IDENTIFYCODE(String TAXPAYER_IDENTIFYCODE) {
    this.TAXPAYER_IDENTIFYCODE = TAXPAYER_IDENTIFYCODE;
  }

  public String getSUPPLIER_DRAWER() {
    return SUPPLIER_DRAWER;
  }

  public void setSUPPLIER_DRAWER(String SUPPLIER_DRAWER) {
    this.SUPPLIER_DRAWER = SUPPLIER_DRAWER;
  }

  public String getBANK_NAME() {
    return BANK_NAME;
  }

  public void setBANK_NAME(String BANK_NAME) {
    this.BANK_NAME = BANK_NAME;
  }

  public String getBANK_ACCOUNT() {
    return BANK_ACCOUNT;
  }

  public void setBANK_ACCOUNT(String BANK_ACCOUNT) {
    this.BANK_ACCOUNT = BANK_ACCOUNT;
  }

  public String getSUPPLIER_LINKMAN() {
    return SUPPLIER_LINKMAN;
  }

  public void setSUPPLIER_LINKMAN(String SUPPLIER_LINKMAN) {
    this.SUPPLIER_LINKMAN = SUPPLIER_LINKMAN;
  }

  public String getSUPPLIER_TELEPHONE() {
    return SUPPLIER_TELEPHONE;
  }

  public void setSUPPLIER_TELEPHONE(String SUPPLIER_TELEPHONE) {
    this.SUPPLIER_TELEPHONE = SUPPLIER_TELEPHONE;
  }

  public String getSUPPLIER_MOBILEPHONE() {
    return SUPPLIER_MOBILEPHONE;
  }

  public void setSUPPLIER_MOBILEPHONE(String SUPPLIER_MOBILEPHONE) {
    this.SUPPLIER_MOBILEPHONE = SUPPLIER_MOBILEPHONE;
  }

  public String getSUPPLIER_FAX() {
    return SUPPLIER_FAX;
  }

  public void setSUPPLIER_FAX(String SUPPLIER_FAX) {
    this.SUPPLIER_FAX = SUPPLIER_FAX;
  }

  public String getSUPPLIER_EMAIL() {
    return SUPPLIER_EMAIL;
  }

  public void setSUPPLIER_EMAIL(String SUPPLIER_EMAIL) {
    this.SUPPLIER_EMAIL = SUPPLIER_EMAIL;
  }

  public String getSUPPLIER_ADDRESS() {
    return SUPPLIER_ADDRESS;
  }

  public void setSUPPLIER_ADDRESS(String SUPPLIER_ADDRESS) {
    this.SUPPLIER_ADDRESS = SUPPLIER_ADDRESS;
  }

  public Integer getSUPPLIER_STATE() {
    return SUPPLIER_STATE;
  }

  public void setSUPPLIER_STATE(Integer SUPPLIER_STATE) {
    this.SUPPLIER_STATE = SUPPLIER_STATE;
  }

  public String getRTWECHATPUSH_IDS() {
    return RTWECHATPUSH_IDS;
  }

  public void setRTWECHATPUSH_IDS(String RTWECHATPUSH_IDS) {
    this.RTWECHATPUSH_IDS = RTWECHATPUSH_IDS;
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

  public String getSUPPLIER_DESC() {
    return SUPPLIER_DESC;
  }

  public void setSUPPLIER_DESC(String SUPPLIER_DESC) {
    this.SUPPLIER_DESC = SUPPLIER_DESC;
  }

  public Integer getMERCHANTTYPE_ID() {
    return MERCHANTTYPE_ID;
  }

  public void setMERCHANTTYPE_ID(Integer MERCHANTTYPE_ID) {
    this.MERCHANTTYPE_ID = MERCHANTTYPE_ID;
  }

  public String getMERCHANTTYPE_NAME() {
    return MERCHANTTYPE_NAME;
  }

  public void setMERCHANTTYPE_NAME(String MERCHANTTYPE_NAME) {
    this.MERCHANTTYPE_NAME = MERCHANTTYPE_NAME;
  }
}
