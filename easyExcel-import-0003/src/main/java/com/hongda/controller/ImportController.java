package com.hongda.controller;

import com.alibaba.excel.EasyExcel;
import com.hongda.config.UserExcelReadListener;
import com.hongda.data.UserData;
import java.io.IOException;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Description TODO
 * @Author lyb
 * @Date 2024/8/19 17:17
 */
@RestController
@RequestMapping("/import")
public class ImportController {
  /**
   * 从Excel导入会员列表
   */
  @RequestMapping(value = "/test3", method = RequestMethod.POST)
  public void importMemberList(@RequestPart("file") MultipartFile file) throws IOException {

    EasyExcel.read(file.getInputStream(), UserData.class, new UserExcelReadListener()).sheet().doRead();

  }

  @RequestMapping(value = "/test4", method = RequestMethod.POST)
  public void importMemberList1(@RequestPart("file") MultipartFile file) throws IOException {


    List<UserData> list = EasyExcel.read(file.getInputStream())
        .head(UserData.class)
        .sheet()
        .doReadSync();
    for (UserData member : list) {
      System.out.println(member);
    }

  }

}
