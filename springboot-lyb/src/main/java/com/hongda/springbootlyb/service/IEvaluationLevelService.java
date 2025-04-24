package com.hongda.springbootlyb.service;

import com.hongda.springbootlyb.pojo.dto.EvaluationLevelDTO;
import com.hongda.springbootlyb.pojo.page.PagePara;
import com.hongda.springbootlyb.pojo.page.PageResultS;
import com.hongda.springbootlyb.pojo.vo.EvaluationLevelVO;

public interface IEvaluationLevelService {

  /**
   * 根据类型查询数据
   * @param pagePara
   * @return
   */
  PageResultS<EvaluationLevelVO> findByType(PagePara pagePara);

  /**
   * 添加数据
   * @param levelDTO
   * @return
   */

  EvaluationLevelVO addEvaluationLevel(EvaluationLevelDTO levelDTO);

  /**
   * 根据id查询
   * @param id
   * @return
   */
  EvaluationLevelVO findById(Integer id);
}
