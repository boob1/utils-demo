package com.hongda.springbootlyb.controller;

import com.hongda.springbootlyb.pojo.dto.SupplierEvaluationDTO;
import com.hongda.springbootlyb.pojo.page.PageResultS;
import com.hongda.springbootlyb.pojo.page.ResponseMessage;
import com.hongda.springbootlyb.pojo.page.ResponsePageMessage;
import com.hongda.springbootlyb.pojo.page.SupplierEvaluationPage;
import com.hongda.springbootlyb.pojo.vo.SupplierEvaluationVO;
import com.hongda.springbootlyb.service.ISupplierEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 供应商考核
 * @Author lyb
 * @Date 2025/4/24 0:11
 */
@RestController
@RequestMapping("/Evaluate")
public class SupplierEvaluationController {
  @Autowired
  private ISupplierEvaluationService supplierEvaluationService;
  @PostMapping("/SynchroSupplierEvaluationAndDetail")
  public ResponseMessage addSupplierEvaluation(@RequestBody SupplierEvaluationDTO dto){
    SupplierEvaluationVO levelVO = supplierEvaluationService.addSupplierEvaluation(dto);
    return ResponseMessage.success(null,"同步成功");
  }

  @PostMapping("/GetSUPPLIEREVALUATIONList")
  public ResponsePageMessage findList(@RequestBody SupplierEvaluationPage page){
    PageResultS<SupplierEvaluationVO> user = supplierEvaluationService.findList(page);
    return ResponsePageMessage.success(user,"查询成功");
  }

  @GetMapping("/GetSUPPLIEREVALUATIONDetail")
  public ResponseMessage findById(@RequestParam("SUPPLIEREVALUATIONId") Integer id ){
    SupplierEvaluationVO levelVO = supplierEvaluationService.findById(id);
    return ResponseMessage.success(levelVO,"查询成功");
  }
}
