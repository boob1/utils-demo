package com.hongda.springbootlyb.service;

import com.hongda.springbootlyb.pojo.page.PageResultS;
import com.hongda.springbootlyb.pojo.page.SupplierEvaluationInfoPage;
import com.hongda.springbootlyb.pojo.vo.SupplierEvaluationInfoVO;

public interface ISupplierEvaluationInfoService {

  PageResultS<SupplierEvaluationInfoVO> findInfoList(SupplierEvaluationInfoPage page);
}
