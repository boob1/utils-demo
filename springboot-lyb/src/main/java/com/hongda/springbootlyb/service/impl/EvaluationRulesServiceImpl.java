package com.hongda.springbootlyb.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hongda.springbootlyb.dao.mapper.EvaluationRulesMapper;
import com.hongda.springbootlyb.dao.EvaluationRulesRepository;
import com.hongda.springbootlyb.pojo.EvaluationRules;
import com.hongda.springbootlyb.pojo.dto.EvaluationRulesDTO;
import com.hongda.springbootlyb.pojo.page.EvaluationRulesPage;
import com.hongda.springbootlyb.pojo.page.PageResultS;
import com.hongda.springbootlyb.pojo.vo.EvaluationRulesVO;
import com.hongda.springbootlyb.service.IEvaluationRulesService;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @Description 考核规则表业务处理
 * @Author lyb
 * @Date 2025/4/23 23:25
 */
@Service
public class EvaluationRulesServiceImpl implements IEvaluationRulesService {

  @Autowired
  EvaluationRulesRepository rulesRepository;

  @Autowired
  EvaluationRulesMapper evaluationRulesMapper;

    @Override
  public EvaluationRulesVO addEvaluationRules(EvaluationRulesDTO evaluationRulesDTO) {
    EvaluationRules evaluationRules = new EvaluationRules();
    BeanUtils.copyProperties(evaluationRulesDTO, evaluationRules);
    EvaluationRules evaluationLevelNew = rulesRepository.save(evaluationRules);

    EvaluationRulesVO levelVO = new EvaluationRulesVO();
    BeanUtils.copyProperties(evaluationLevelNew, levelVO);
    return levelVO;
  }

  @Override
  public PageResultS<EvaluationRulesVO> findList(EvaluationRulesPage pagePara) {
    // 校验分页参数
    if (pagePara.getPageIndex() < 0 || pagePara.getPageSize() <= 0) {
      throw new IllegalArgumentException("分页参数不合法");
    }

    // 构造分页参数
    Pageable pageable = PageRequest.of(pagePara.getPageIndex() - 1, pagePara.getPageSize());

    try {
      // 将逗号拼接的字符串转换为 Long 类型的列表
      List<Integer> evaluationDepartmentIds = Arrays.stream(
              pagePara.getSearchParameter().getEVALUATION_DEPARTMENT_IDS().split(","))
          .map(Integer::parseInt)
          .collect(Collectors.toList());

      // 调用 Repository 方法进行分页查询
      Page<EvaluationRules> evaluationLevels = rulesRepository.findList(
          pagePara.getSearchParameter().getEVALUATIONRULES_STATE(),
          evaluationDepartmentIds, pageable);

      // 创建 VO 列表
      List<EvaluationRulesVO> voList = evaluationLevels.getContent().stream()
          .map(this::convertToVO)
          .collect(Collectors.toList());

      // 构造分页结果
      PageResultS<EvaluationRulesVO> pageResult = new PageResultS<>();
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
  public void deleteById(Integer id) {
    rulesRepository.deleteById(id);
  }

  @Override
  public PageResultS<EvaluationRulesVO> findEditData(EvaluationRulesPage pagePara) {
    // 校验分页参数
    if (pagePara.getPageIndex() < 0 || pagePara.getPageSize() <= 0) {
      throw new IllegalArgumentException("分页参数不合法");
    }

    try {
      // 将逗号拼接的字符串转换为 Long 类型的列表
      List<Integer> evaluationDepartmentIds = Arrays.stream(
              pagePara.getSearchParameter().getEVALUATION_DEPARTMENT_IDS().split(","))
          .map(Integer::parseInt)
          .collect(Collectors.toList());

      // 调用 Repository 方法进行分页查询
      List<EvaluationRules> evaluationLevels = evaluationRulesMapper.findEditData(
          pagePara.getSearchParameter().getEVALUATIONRULES_STATE(),
          evaluationDepartmentIds, pagePara.getSearchParameter().getOWNERUNIT_ID(),
          pagePara.getSearchParameter().getPROVINCE_CODE());

      PageHelper.startPage(pagePara.getPageIndex(), pagePara.getPageSize());

      // 获取分页信息
      PageInfo<EvaluationRules> pageInfo = new PageInfo<>(evaluationLevels);

      // 创建 VO 列表
      List<EvaluationRulesVO> voList = pageInfo.getList().stream()
          .map(this::convertToVO)
          .collect(Collectors.toList());

      // 构造分页结果
      PageResultS<EvaluationRulesVO> pageResult = new PageResultS<>();
      pageResult.setTotalCount(pageInfo.getTotal());
      pageResult.setPageIndex(pageInfo.getPages()); // 页码从 1 开始
      pageResult.setPageSize(pagePara.getPageSize());
      pageResult.setList(voList);

      return pageResult;
    } catch (Exception e) {
      // 捕获异常并抛出自定义异常
      throw new RuntimeException("分页查询失败：" + e.getMessage(), e);
    }
  }

  @Override
  public EvaluationRulesVO findById(Integer id) {
    Optional<EvaluationRules> repository = rulesRepository.findById(id);
    if (repository.isPresent()) {
      EvaluationRules level = repository.get();
      EvaluationRulesVO levelVO = new EvaluationRulesVO();
      BeanUtils.copyProperties(level, levelVO);
      return levelVO;
    }
    return null;
  }

  private EvaluationRulesVO convertToVO(EvaluationRules evaluationRules) {
    EvaluationRulesVO vo = new EvaluationRulesVO();
    BeanUtils.copyProperties(evaluationRules, vo);
    vo.setUSER_ID(4366);
    return vo;
  }
}
