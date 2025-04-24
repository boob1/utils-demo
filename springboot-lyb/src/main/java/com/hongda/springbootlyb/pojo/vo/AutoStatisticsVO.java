package com.hongda.springbootlyb.pojo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import lombok.Data;

/**
 * @Description
 * @Author lyb
 * @Date 2025/4/23 17:08
 */
@Data
public class AutoStatisticsVO {
  @JsonProperty("AUTOSTATISTICS_ID")
  private Integer AUTOSTATISTICS_ID;
  @JsonProperty("AUTOSTATISTICS_PID")
  private Integer AUTOSTATISTICS_PID;
  @JsonProperty("AUTOSTATISTICS_NAME")
  private String AUTOSTATISTICS_NAME;
  @JsonProperty("AUTOSTATISTICS_VALUE")
  private String AUTOSTATISTICS_VALUE;
  @JsonProperty("AUTOSTATISTICS_INDEX")
  private Integer AUTOSTATISTICS_INDEX;
  @JsonProperty("AUTOSTATISTICS_TYPE")
  private Integer AUTOSTATISTICS_TYPE;
  @JsonProperty("STATISTICS_TYPE")
  private Integer STATISTICS_TYPE;
  @JsonProperty("AUTOSTATISTICS_ICO")
  private String AUTOSTATISTICS_ICO;
  @JsonProperty("OWNERUNIT_ID")
  private Integer OWNERUNIT_ID;
  @JsonProperty("OWNERUNIT_NAME")
  private String OWNERUNIT_NAME;
  @JsonProperty("PROVINCE_CODE")
  private Integer PROVINCE_CODE;
  @JsonProperty("AUTOSTATISTICS_STATE")
  private Integer AUTOSTATISTICS_STATE;
  @JsonProperty("STAFF_ID")
  private Integer STAFF_ID;
  @JsonProperty("STAF_NAME")
  private String STAF_NAME;
  @JsonProperty("OPERATE_DATE")
  private Date OPERATE_DATE;
  @JsonProperty("AUTOSTATISTICS_DESC")
  private String AUTOSTATISTICS_DESC;
}
