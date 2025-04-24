package com.hongda.springbootlyb.service;

import com.hongda.springbootlyb.pojo.dto.EvaluationRulesDTO;
import com.hongda.springbootlyb.pojo.page.EvaluationRulesPage;
import com.hongda.springbootlyb.pojo.page.PageResultS;
import com.hongda.springbootlyb.pojo.vo.EvaluationRulesVO;

public interface IEvaluationRulesService {

  /**
   * 保存数据
   * @param evaluationRulesDTO
   * @return
   */
  EvaluationRulesVO addEvaluationRules(EvaluationRulesDTO evaluationRulesDTO);
  /**
   * 查询数据
   * @param page
   * @return
   */
  PageResultS<EvaluationRulesVO> findList(EvaluationRulesPage page);

  /**
   * 根据id删除数据
   * @param autoStatisticsId
   */
  void deleteById(Integer autoStatisticsId);

  PageResultS<EvaluationRulesVO> findEditData(EvaluationRulesPage page);

  EvaluationRulesVO findById(Integer id);
}
