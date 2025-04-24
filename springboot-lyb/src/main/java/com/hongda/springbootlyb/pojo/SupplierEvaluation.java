package com.hongda.springbootlyb.pojo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * @Description供应商考评信息表
 * @Author lyb
 * @Date 2025/4/23 10:25
 */
@Entity
@Data
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
  private String EVALUATION_DEGREE;
  private String STATISTICS_TYPE;
  private Integer SUPPLIEREVALUATION_STATE;
  private Integer STAFF_ID;
  private String STAFF_NAME;
  private Date OPERATE_DATE;
  private String SUPPLIEREVALUATION_DESC;
  private BigDecimal TOTAL_SCORE;


}
