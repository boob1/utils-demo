package com.hongda.springbootlyb.service.impl;

import com.hongda.springbootlyb.mapper.SupplierEvaluationInfoRepository;
import com.hongda.springbootlyb.mapper.SupplierEvaluationRepository;
import com.hongda.springbootlyb.pojo.SupplierEvaluation;
import com.hongda.springbootlyb.pojo.SupplierEvaluationInfo;
import com.hongda.springbootlyb.pojo.dto.SupplierEvaluationDTO;
import com.hongda.springbootlyb.pojo.dto.SupplierEvaluationInfoDTO;
import com.hongda.springbootlyb.pojo.page.PageResultS;
import com.hongda.springbootlyb.pojo.page.SupplierEvaluationPage;
import com.hongda.springbootlyb.pojo.vo.SupplierEvaluationVO;
import com.hongda.springbootlyb.service.ISupplierEvaluationService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description 供应商考评信息表业务处理
 * @Author lyb
 * @Date 2025/4/24 0:13
 */
@Service
@Slf4j
public class SupplierEvaluationServiceImpl implements ISupplierEvaluationService {

  @Autowired
  SupplierEvaluationRepository supplierEvaluationRepository;
  @Autowired
  SupplierEvaluationInfoRepository infoRepository;

  @Override
  public PageResultS<SupplierEvaluationVO> findList(SupplierEvaluationPage pagePara) {
    // 校验分页参数
    if (pagePara.getPageIndex() < 0 || pagePara.getPageSize() <= 0) {
      throw new IllegalArgumentException("分页参数不合法");
    }

    // 构造分页参数
    Pageable pageable = PageRequest.of(pagePara.getPageIndex() - 1, pagePara.getPageSize());

    try {
      // 将逗号拼接的字符串转换为 Long 类型的列表
      List<Integer> evaluationDepartmentIds = Arrays.stream(
              pagePara.getSearchParameter().getSUPPLIER_IDS().split(","))
          .map(Integer::parseInt)
          .collect(Collectors.toList());

      // 调用 Repository 方法进行分页查询
      Page<SupplierEvaluation> evaluationLevels = supplierEvaluationRepository.findList(
          evaluationDepartmentIds, pageable);

      // 创建 VO 列表
      List<SupplierEvaluationVO> voList = evaluationLevels.getContent().stream()
          .map(this::convertToVO)
          .collect(Collectors.toList());

      // 构造分页结果
      PageResultS<SupplierEvaluationVO> pageResult = new PageResultS<>();
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

  @Override
  @Transactional(rollbackFor = Exception.class)
  public SupplierEvaluationVO addSupplierEvaluation(SupplierEvaluationDTO dto) {
    if (dto == null || dto.getDetailList() == null) {
      throw new IllegalArgumentException("Input DTO or detail list cannot be null");
    }

    try {
      // 主表数据保存
      SupplierEvaluation supplierEvaluation = new SupplierEvaluation();
      BeanUtils.copyProperties(dto, supplierEvaluation);
      SupplierEvaluation savedEvaluation = supplierEvaluationRepository.save(supplierEvaluation);

      // 子表数据保存
      List<SupplierEvaluationInfo> detailListNew = saveDetailList(dto.getDetailList(), savedEvaluation.getSUPPLIEREVALUATION_ID());
      infoRepository.saveAll(detailListNew);

      // 构建并返回 VO 对象
      return buildSupplierEvaluationVO(savedEvaluation, detailListNew);
    } catch (Exception e) {
      // 异常处理：记录日志并抛出自定义异常
      log.error("Error occurred while saving supplier evaluation", e);
      throw new RuntimeException("Failed to save supplier evaluation", e);
    }
  }

  @Override
  public SupplierEvaluationVO findById(Integer id) {
    Optional<SupplierEvaluation> supplierEvaluation = supplierEvaluationRepository.findById(id);
    // 处理返回结果
    if (!supplierEvaluation.isPresent()) {
      return null;
    }
    SupplierEvaluation supplierEvaluationEntity = supplierEvaluation.get();
    SupplierEvaluationVO supplierEvaluationVO = new SupplierEvaluationVO();
    BeanUtils.copyProperties(supplierEvaluationEntity, supplierEvaluationVO);
    return supplierEvaluationVO;

  }


  /**
   * 保存子表数据
   */
  private List<SupplierEvaluationInfo> saveDetailList(List<SupplierEvaluationInfoDTO> detailList, Integer parentEvaluationId) {
    if (detailList == null || detailList.isEmpty()) {
      return List.of(); // 返回空列表
    }

    return detailList.stream()
        .map(t -> {
          SupplierEvaluationInfo info = new SupplierEvaluationInfo();
          BeanUtils.copyProperties(t, info);
          info.setSUPPLIEREVALUATION_ID(parentEvaluationId); // 设置外键关联
          return info;
        })
        .collect(Collectors.toList());
  }

  /**
   * 构建 SupplierEvaluationVO
   */
  private SupplierEvaluationVO buildSupplierEvaluationVO(SupplierEvaluation evaluation, List<SupplierEvaluationInfo> detailList) {
    SupplierEvaluationVO vo = new SupplierEvaluationVO();
    BeanUtils.copyProperties(evaluation, vo);
    vo.setDetailList(detailList); // 设置子表数据
    return vo;
  }

    private SupplierEvaluationVO convertToVO(SupplierEvaluation supplierEvaluation) {
    SupplierEvaluationVO vo = new SupplierEvaluationVO();
    BeanUtils.copyProperties(supplierEvaluation, vo);
    return vo;
  }
}
