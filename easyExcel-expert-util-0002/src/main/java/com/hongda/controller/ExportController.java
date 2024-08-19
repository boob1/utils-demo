package com.hongda.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.hongda.entity.data.Mock;
import com.hongda.entity.sheet.CitySheet;
import com.hongda.entity.sheet.CompanySheet;
import com.hongda.entity.sheet.UserSheet;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * @Description TODO
 * @Author lyb
 * @Date 2024/8/19 16:10
 */
@RestController
@RequestMapping("/export")
public class ExportController {

  /**
   * @param response
   * @url <a>http://localhost:8080/export/test1</a> 在Excel中写入单个sheet
   */
  @PostMapping("/test1")
  public void test1(HttpServletResponse response) {
    //从HttpServletResponse中获取OutputStream输出流
    try {
      // 设置响应类型
      response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
      // 设置编码格式
      response.setCharacterEncoding("utf-8");
      // 设置URLEncoder.encode 防止中文乱码
      String fileName = URLEncoder.encode("用户信息表", "UTF-8").replaceAll("\\+", "%20");
      // 设置响应头
      response.setHeader("Content-disposition",
          "attachment;filename*=utf-8''" + fileName + ".xlsx");
      // 写出Excel
      EasyExcel.write(response.getOutputStream(), UserSheet.class)
          .inMemory(true)
          .sheet("用户信息表")
          .doWrite(Mock.userList());
    } catch (IOException e) {
      throw new RuntimeException("数据或文件损坏，无法下载");
    }
  }

  /**
   * 在Excel中写入多个sheet
   *
   * @url <a>http://localhost:8080/export/test2</a>
   */
  @RequestMapping("/test2")
  public void test2(HttpServletResponse response) throws Exception {
    // 设置响应类型
    response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    // 设置编码格式
    response.setCharacterEncoding("utf-8");
    // 设置URLEncoder.encode 防止中文乱码
    String fileName = URLEncoder.encode("信息表", "UTF-8").replaceAll("\\+", "%20");
    // 设置响应头
    response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
    // 多个sheet的输出需要使用ExcelWriter类，这里想要下载成功，需要输出到OutputStream中
    try (ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).inMemory(true)
        .build()) {

      // 创建用户信息表的sheet，写入用户信息数据，1代表sheet的位置是第一个
      WriteSheet userInfoSheet = EasyExcel.writerSheet(0, "用户信息表")
          .head(UserSheet.class)
          .build();
      excelWriter.write(Mock.userList(), userInfoSheet);

      // 创建城市信息表的sheet，写入城市信息数据，2代表sheet的位置是第二个
      WriteSheet cityInfoSheet = EasyExcel.writerSheet(1, "城市信息表")
          .head(CitySheet.class)
          .build();
      excelWriter.write(Mock.cityList(), cityInfoSheet);

      // 创建公司信息表的sheet，写入公司信息数据，3代表sheet的位置是第三个
      WriteSheet companyInfoSheet = EasyExcel.writerSheet(2, "公司信息表")
          .head(CompanySheet.class)
          .build();
      excelWriter.write(Mock.companyList(), companyInfoSheet);
    }
  }
}
