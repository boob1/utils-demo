package com.hongda.entity.sheet;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Builder;
import lombok.Data;

/**
 * @Description TODO
 * @Author lyb
 * @Date 2024/8/19 16:14
 */
@Data
@Builder
public class CitySheet {

  @ExcelProperty(value = "城市名称", index = 0)
  @ColumnWidth(10)
  private String cityName;

  @ExcelProperty(value = "城市介绍", index = 1)
  @ColumnWidth(60)
  private String cityDesc;

}

