package com.hongda.springbootlyb.service.impl;

import com.hongda.springbootlyb.dao.SupplierEvaluationInfoRepository;
import com.hongda.springbootlyb.pojo.SupplierEvaluation;
import com.hongda.springbootlyb.pojo.SupplierEvaluationInfo;
import com.hongda.springbootlyb.pojo.page.PageResultS;
import com.hongda.springbootlyb.pojo.page.SupplierEvaluationInfoPage;
import com.hongda.springbootlyb.pojo.vo.SupplierEvaluationInfoVO;
import com.hongda.springbootlyb.pojo.vo.SupplierEvaluationVO;
import com.hongda.springbootlyb.service.ISupplierEvaluationInfoService;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author lyb
 * @Date 2025/4/24 18:05
 */
@Service
public class SupplierEvaluationInfoServiceImpl implements ISupplierEvaluationInfoService {

  @Autowired
  SupplierEvaluationInfoRepository supplierEvaluationInfoRepository;
  @Override
  public PageResultS<SupplierEvaluationInfoVO> findInfoList(SupplierEvaluationInfoPage pagePara) {
    // 校验分页参数
    if (pagePara.getPageIndex() < 0 || pagePara.getPageSize() <= 0) {
      throw new IllegalArgumentException("分页参数不合法");
    }

    // 构造分页参数
    Pageable pageable = PageRequest.of(pagePara.getPageIndex() - 1, pagePara.getPageSize());

    try {


      // 调用 Repository 方法进行分页查询
      Page<SupplierEvaluationInfo> evaluationLevels = supplierEvaluationInfoRepository.findInfoList(
          pagePara.getSearchParameter().getSUPPLIEREVALUATION_ID(), pageable);

      // 创建 VO 列表
      List<SupplierEvaluationInfoVO> voList = evaluationLevels.getContent().stream()
          .map(this::convertToVO)
          .collect(Collectors.toList());

      // 构造分页结果
      PageResultS<SupplierEvaluationInfoVO> pageResult = new PageResultS<>();
      pageResult.setTotalCount(evaluationLevels.getTotalElements());
      pageResult.setPageIndex(evaluationLevels.getNumber() + 1); // 页码从 1 开始
      pageResult.setPageSize(evaluationLevels.getSize());
      pageResult.setList(voList);

      return pageResult;
    } catch (Exception e) {
      // 捕获异常并抛出自定义异常
      throw new RuntimeException("分页查询失败：" + e.getMessage(), e);
    }
  }

  private SupplierEvaluationInfoVO convertToVO(SupplierEvaluationInfo supplierEvaluation) {
    SupplierEvaluationInfoVO vo = new SupplierEvaluationInfoVO();
    BeanUtils.copyProperties(supplierEvaluation, vo);
    vo.setUSER_ID(4366);
    return vo;
  }
}
