package com.hongda.springbootlyb.service;

import com.hongda.springbootlyb.pojo.dto.EvaluationLevelDTO;
import com.hongda.springbootlyb.pojo.page.PagePara;
import com.hongda.springbootlyb.pojo.page.PageResultS;
import com.hongda.springbootlyb.pojo.vo.EvaluationLevelVO;

public interface IEvaluationLevelService {

  PageResultS<EvaluationLevelVO> findByType(PagePara pagePara);

  EvaluationLevelVO addEvaluationLevel(EvaluationLevelDTO levelDTO);
}
