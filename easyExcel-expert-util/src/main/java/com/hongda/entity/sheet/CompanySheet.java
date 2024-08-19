package com.hongda.entity.sheet;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Builder;
import lombok.Data;

/**
 * @Description TODO
 * @Author lyb
 * @Date 2024/8/19 16:15
 */
@Data
@Builder
public class CompanySheet {

  @ExcelProperty(value = "公司名称", index = 0)
  @ColumnWidth(10)
  private String companyName;

  @ExcelProperty(value = "公司创始人", index = 1)
  @ColumnWidth(10)
  private String companyBoss;

  @ExcelProperty(value = "公司总基地", index = 2)
  @ColumnWidth(10)
  private String companyBase;

  @ExcelProperty(value = "公司简介", index = 3)
  @ColumnWidth(50)
  private String companyDesc;
}