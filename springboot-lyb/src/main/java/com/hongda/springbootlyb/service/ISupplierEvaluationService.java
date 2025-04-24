package com.hongda.springbootlyb.service;

import com.hongda.springbootlyb.pojo.dto.SupplierEvaluationDTO;
import com.hongda.springbootlyb.pojo.page.PageResultS;
import com.hongda.springbootlyb.pojo.page.SupplierEvaluationPage;
import com.hongda.springbootlyb.pojo.vo.SupplierEvaluationVO;

public interface ISupplierEvaluationService {

  /**
   * 查询列表
   * @param page
   * @return
   */
  PageResultS<SupplierEvaluationVO> findList(SupplierEvaluationPage page);

  /**
   * 保存数据
   * @param dto
   * @return
   */
  SupplierEvaluationVO addSupplierEvaluation(SupplierEvaluationDTO dto);

  SupplierEvaluationVO findById(Integer id);
}
