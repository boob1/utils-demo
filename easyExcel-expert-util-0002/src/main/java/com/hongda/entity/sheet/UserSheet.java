package com.hongda.entity.sheet;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.metadata.data.WriteCellData;
import java.util.Date;
import lombok.Builder;
import lombok.Data;

/**
 * @Description TODO
 * @Author lyb
 * @Date 2024/8/19 16:15
 */
@Data
@Builder
public class UserSheet {

  @ExcelProperty(value = "用户ID", index = 0)
  @ColumnWidth(10)
  private Long userId;

  @ExcelProperty(value = "用户名称", index = 1)
  @ColumnWidth(10)
  private String userName;

  @ExcelProperty(value = {"基本信息", "手机号码"}, index = 2)
  @ColumnWidth(20)
  private String userPhone;

  @ExcelProperty(value = {"基本信息", "电子邮箱"}, index = 3)
  @ColumnWidth(20)
  private String userEmail;

  @ExcelProperty(value = {"基本信息", "地址"}, index = 4)
  @ColumnWidth(20)
  private String userAddress;

  @ExcelProperty(value = "注册时间", index = 5)
  @ColumnWidth(20)
  private Date registerTime;

  @ExcelProperty(value = "性别，男:红色/女:绿色")
  @ColumnWidth(30)
  private WriteCellData<String> gender;

  /**
   * 忽略这个字段
   */
  @ExcelIgnore
  private Integer age;
}
