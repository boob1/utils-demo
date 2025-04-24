package com.hongda.springbootlyb.controller;

import com.hongda.springbootlyb.pojo.page.PageResultS;
import com.hongda.springbootlyb.pojo.page.ResponsePageMessage;
import com.hongda.springbootlyb.pojo.page.SupplierEvaluationInfoPage;
import com.hongda.springbootlyb.pojo.vo.SupplierEvaluationInfoVO;
import com.hongda.springbootlyb.service.ISupplierEvaluationInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author lyb
 * @Date 2025/4/24 18:04
 */
@RestController
@RequestMapping("/Evaluate")
public class SupplierEvaluationInfoController {
  @Autowired
  ISupplierEvaluationInfoService supplierEvaluationInfoService;
  @PostMapping("/GetSUPPLIEREVALUATIONINFOViewList")
  public ResponsePageMessage findInfoList(@RequestBody SupplierEvaluationInfoPage page){
    PageResultS<SupplierEvaluationInfoVO> pageResultS = supplierEvaluationInfoService.findInfoList(page);
    return ResponsePageMessage.success(pageResultS,"查询成功");
  }
}
