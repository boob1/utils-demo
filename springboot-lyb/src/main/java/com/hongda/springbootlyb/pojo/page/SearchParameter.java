package com.hongda.springbootlyb.pojo.page;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Description
 * @Author lyb
 * @Date 2025/4/23 13:37
 */
public class SearchParameter {

  @JsonProperty("EVALUATIONLEVEL_TYPE")
  private Integer EVALUATIONLEVEL_TYPE;
  @JsonProperty("OWNERUNIT_ID")
  private Integer OWNERUNIT_ID;
  @JsonProperty("PROVINCE_CODE")
  private String PROVINCE_CODE;


  public Integer getOWNERUNIT_ID() {
    return OWNERUNIT_ID;
  }

  public void setOWNERUNIT_ID(Integer OWNERUNIT_ID) {
    this.OWNERUNIT_ID = OWNERUNIT_ID;
  }

  public String getPROVINCE_CODE() {
    return PROVINCE_CODE;
  }

  public void setPROVINCE_CODE(String PROVINCE_CODE) {
    this.PROVINCE_CODE = PROVINCE_CODE;
  }

  public Integer getEVALUATIONLEVEL_TYPE() {
    return EVALUATIONLEVEL_TYPE;
  }

  public void setEVALUATIONLEVEL_TYPE(Integer EVALUATIONLEVEL_TYPE) {
    this.EVALUATIONLEVEL_TYPE = EVALUATIONLEVEL_TYPE;
  }
}
