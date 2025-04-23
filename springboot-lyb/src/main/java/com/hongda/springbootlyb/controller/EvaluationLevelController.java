package com.hongda.springbootlyb.controller;

import com.hongda.springbootlyb.pojo.User;
import com.hongda.springbootlyb.pojo.dto.EvaluationLevelDTO;
import com.hongda.springbootlyb.pojo.dto.UserDTO;
import com.hongda.springbootlyb.pojo.page.PagePara;
import com.hongda.springbootlyb.pojo.page.PageResultS;
import com.hongda.springbootlyb.pojo.page.ResponseMessage;
import com.hongda.springbootlyb.pojo.page.ResponsePageMessage;
import com.hongda.springbootlyb.pojo.vo.EvaluationLevelVO;
import com.hongda.springbootlyb.service.IEvaluationLevelService;
import com.hongda.springbootlyb.service.IUserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author lyb
 * @Date 2025/4/23 13:40
 */
@RestController
@RequestMapping("/Evaluate")
public class EvaluationLevelController {
  @Autowired
  private IEvaluationLevelService evaluationLevelService;

  @PostMapping("/SynchroEVALUATIONLEVEL")
  public ResponseMessage addEvaluationLevel(@RequestBody EvaluationLevelDTO levelDTO){
    EvaluationLevelVO levelVO = evaluationLevelService.addEvaluationLevel(levelDTO);
    return ResponseMessage.success(levelVO,"同步成功");
  }

  @PostMapping("/getEVALUATIONLEVELList")
  public ResponsePageMessage getEVALUATIONLEVELList(@RequestBody PagePara pagePara){
    PageResultS<EvaluationLevelVO> user = evaluationLevelService.findByType(pagePara);
    return ResponsePageMessage.success(user,"查询成功");
  }
}
