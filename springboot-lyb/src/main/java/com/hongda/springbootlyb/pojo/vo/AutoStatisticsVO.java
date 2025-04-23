package com.hongda.springbootlyb.pojo.vo;

import java.util.Date;
import lombok.Data;

/**
 * @Description
 * @Author lyb
 * @Date 2025/4/23 17:08
 */
@Data
public class AutoStatisticsVO {
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
}
