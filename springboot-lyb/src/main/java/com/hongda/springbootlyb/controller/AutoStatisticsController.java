package com.hongda.springbootlyb.controller;

import cn.hutool.log.LogFactory;
import com.hongda.springbootlyb.pojo.dto.AutoStatisticsDTO;
import com.hongda.springbootlyb.pojo.page.PageResultS;
import com.hongda.springbootlyb.pojo.page.ResponseMessage;
import com.hongda.springbootlyb.pojo.page.ResponsePageMessage;
import com.hongda.springbootlyb.pojo.vo.AutoStatisticsVO;
import com.hongda.springbootlyb.pojo.vo.EvaluationLevelVO;
import com.hongda.springbootlyb.service.IAutoStatisticsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import utils.TreeNode;

/**
 * @Description 考核部门管理
 * @Author lyb
 * @Date 2025/4/23 17:05
 */
@RestController
@RequestMapping("/BaseInfo")
public class AutoStatisticsController {
  Logger log= LoggerFactory.getLogger(AutoStatisticsController.class);

  @Autowired
  private IAutoStatisticsService autoStatisticsService;
  @PostMapping("/SynchroAUTOSTATISTICS")
  public ResponseMessage addAutoStatistics(@RequestBody AutoStatisticsDTO autoStatisticsDTO){
    AutoStatisticsVO levelVO = autoStatisticsService.addautoStatistics(autoStatisticsDTO);
    log.info("同步成功");
    log.trace("同步成功");
    log.debug("同步成功");
    log.error("同步成功");
    return ResponseMessage.success(null,"同步成功");
  }


  @PostMapping("/GetAutoStatisticsTreeList")
  public ResponsePageMessage getEVALUATIONLEVELList( @RequestParam("ProvinceCode") String provinceCode,
      @RequestParam("OwnerUnit_Id") int ownerUnitId,
      @RequestParam("AutoStatistics_Type") int autoStatisticsType,
      @RequestParam("AutoStatistics_State") int autoStatisticsState){
    PageResultS<TreeNode> user = autoStatisticsService.findByData(autoStatisticsType,autoStatisticsState);
    return ResponsePageMessage.success(user,"查询成功");
  }

  @GetMapping("/DeleteAUTOSTATISTICS")
  public ResponseMessage deleteById(@RequestParam("AUTOSTATISTICSId") Integer autoStatisticsId ){
     autoStatisticsService.deleteById(autoStatisticsId);
    return ResponseMessage.success(null,"删除成功");
  }

  @GetMapping("/GetAUTOSTATISTICSDetail")
  public ResponseMessage findById(@RequestParam("AUTOSTATISTICSId") Integer id ){
    AutoStatisticsVO levelVO  = autoStatisticsService.findById(id);
    return  ResponseMessage.success(levelVO,"查询成功");
  }

}
