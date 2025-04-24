package com.hongda.springbootlyb.service.impl;

import com.hongda.springbootlyb.dao.EvaluationLevelRepository;
import com.hongda.springbootlyb.pojo.EvaluationLevel;
import com.hongda.springbootlyb.pojo.dto.EvaluationLevelDTO;
import com.hongda.springbootlyb.pojo.page.PagePara;
import com.hongda.springbootlyb.pojo.page.PageResultS;
import com.hongda.springbootlyb.pojo.vo.EvaluationLevelVO;
import com.hongda.springbootlyb.service.IEvaluationLevelService;
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
 * @Description 考评等级表业务处理
 * @Author lyb
 * @Date 2025/4/23 13:41
 */
@Service
public class EvaluationLevelServiceImpl implements IEvaluationLevelService {
  @Autowired
  EvaluationLevelRepository levelRepository;
  @Override
  public PageResultS<EvaluationLevelVO> findByType(PagePara pagePara) {
    // 校验分页参数
    if (pagePara.getPageIndex() < 0 || pagePara.getPageSize() <= 0) {
      throw new IllegalArgumentException("分页参数不合法");
    }

    // 构造分页参数
    Pageable pageable = PageRequest.of(pagePara.getPageIndex()-1, pagePara.getPageSize());

    try {
      // 调用 Repository 方法进行分页查询
      Page<EvaluationLevel> evaluationLevels = levelRepository.findByEvaluationLevelType(pagePara.getSearchParameter().getEVALUATIONLEVEL_TYPE(), pageable);

      // 创建 VO 列表
      List<EvaluationLevelVO> voList = evaluationLevels.getContent().stream()
          .map(this::convertToVO)
          .collect(Collectors.toList());

      // 构造分页结果
      PageResultS<EvaluationLevelVO> pageResult = new PageResultS<>();
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

  // 辅助方法：将 EvaluationLevel 转换为 EvaluationLevelVO
  private EvaluationLevelVO convertToVO(EvaluationLevel evaluationLevel) {
    EvaluationLevelVO vo = new EvaluationLevelVO();
    BeanUtils.copyProperties(evaluationLevel, vo);
    return vo;
  }


  @Override
  public EvaluationLevelVO addEvaluationLevel(EvaluationLevelDTO levelDTO) {
    EvaluationLevel evaluationLevel = new EvaluationLevel();
    BeanUtils.copyProperties(levelDTO, evaluationLevel);
    EvaluationLevel evaluationLevelNew = levelRepository.save(evaluationLevel);

    EvaluationLevelVO levelVO = new EvaluationLevelVO();
    BeanUtils.copyProperties(evaluationLevelNew, levelVO);
    return levelVO;
  }

  @Override
  public EvaluationLevelVO findById(Integer id) {
    Optional<EvaluationLevel> repository = levelRepository.findById(id);
    if (repository.isPresent()) {
      EvaluationLevel level = repository.get();
      EvaluationLevelVO levelVO = new EvaluationLevelVO();
      BeanUtils.copyProperties(level, levelVO);
      return levelVO;
    }
    return null;
  }
}
