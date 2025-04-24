package com.hongda.springbootlyb.pojo.vo;

import com.hongda.springbootlyb.pojo.SupplierEvaluationInfo;
import com.hongda.springbootlyb.pojo.dto.SupplierEvaluationInfoDTO;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import lombok.Data;

/**
 * @Description
 * @Author lyb
 * @Date 2025/4/24 0:07
 */
@Data
public class SupplierEvaluationVO {
  private Integer SUPPLIEREVALUATION_ID;
  private Integer OWNERUNIT_ID;
  private String OWNERUNIT_NAME;
  private Integer PROVINCE_CODE;
  private Integer SUPPLIER_ID;
  private Date EVALUATION_DATE;
  private BigDecimal EVALUATION_SCORE;
  private String EVALUATION_DEGREE;
  private String STATISTICS_TYPE;
  private Integer SUPPLIEREVALUATION_STATE;
  private Integer STAFF_ID;
  private String STAFF_NAME;
  private Date OPERATE_DATE;
  private String SUPPLIEREVALUATION_DESC;
  private BigDecimal TOTAL_SCORE;

  private List<SupplierEvaluationInfo> DetailList;
}
