package com.hongda.springbootlyb.controller;

import com.hongda.springbootlyb.pojo.dto.EvaluationRulesDTO;
import com.hongda.springbootlyb.pojo.page.EvaluationRulesPage;
import com.hongda.springbootlyb.pojo.page.PageResultS;
import com.hongda.springbootlyb.pojo.page.ResponseMessage;
import com.hongda.springbootlyb.pojo.page.ResponsePageMessage;
import com.hongda.springbootlyb.pojo.vo.EvaluationRulesVO;
import com.hongda.springbootlyb.service.IEvaluationRulesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description供应商考核规则
 * @Author lyb
 * @Date 2025/4/23 23:24
 */
@RestController
@RequestMapping("/Evaluate")
public class EvaluationRulesController {

  @Autowired
  private IEvaluationRulesService rulesService;
  @PostMapping("/SynchroEVALUATIONRULES")
  public ResponseMessage addEvaluationRules(@RequestBody EvaluationRulesDTO evaluationRulesDTO){
    EvaluationRulesVO levelVO = rulesService.addEvaluationRules(evaluationRulesDTO);
    return ResponseMessage.success(null,"同步成功");
  }

  @PostMapping("/GetEVALUATIONRULESList")
  public ResponsePageMessage findList(@RequestBody EvaluationRulesPage page){
    PageResultS<EvaluationRulesVO> user = rulesService.findList(page);
    return ResponsePageMessage.success(user,"查询成功");
  }

  @GetMapping("/DeleteEVALUATIONRULES")
  public ResponseMessage deleteById(@RequestParam("EVALUATIONRULESId") Integer autoStatisticsId ){
    rulesService.deleteById(autoStatisticsId);
    return ResponseMessage.success(null,"删除成功");
  }

  @PostMapping("/GetEVALUATIONRULESViewList")
  public ResponsePageMessage findEditData(@RequestBody EvaluationRulesPage page){
    PageResultS<EvaluationRulesVO> user = rulesService.findEditData(page);
    return ResponsePageMessage.success(user,"查询成功");
  }

  @GetMapping("/GetEVALUATIONRULESDetail")
  public ResponseMessage findById(@RequestParam("EVALUATIONRULESId") Integer id ){
    EvaluationRulesVO levelVO  = rulesService.findById(id);
    return  ResponseMessage.success(levelVO,"查询成功");
  }
}
