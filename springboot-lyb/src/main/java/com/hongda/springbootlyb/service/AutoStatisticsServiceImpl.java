package com.hongda.springbootlyb.service;

import com.hongda.springbootlyb.mapper.AutoStatisticsRepository;
import com.hongda.springbootlyb.mapper.EvaluationLevelRepository;
import com.hongda.springbootlyb.pojo.AutoStatistics;
import com.hongda.springbootlyb.pojo.EvaluationLevel;
import com.hongda.springbootlyb.pojo.dto.AutoStatisticsDTO;
import com.hongda.springbootlyb.pojo.page.PagePara;
import com.hongda.springbootlyb.pojo.page.PageResultS;
import com.hongda.springbootlyb.pojo.vo.AutoStatisticsVO;
import com.hongda.springbootlyb.pojo.vo.EvaluationLevelVO;
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
 * @Date 2025/4/23 17:06
 */
@Service
public class AutoStatisticsServiceImpl implements IAutoStatisticsService {

  @Autowired
  AutoStatisticsRepository autoStatisticsRepository;

  @Override
  public AutoStatisticsVO addautoStatistics(AutoStatisticsDTO autoStatisticsDTO) {
    AutoStatistics autoStatistics = new AutoStatistics();
    BeanUtils.copyProperties(autoStatisticsDTO, autoStatistics);
    AutoStatistics autoStatistics1 = autoStatisticsRepository.save(autoStatistics);

    AutoStatisticsVO levelVO = new AutoStatisticsVO();
    BeanUtils.copyProperties(autoStatistics1, levelVO);
    return levelVO;
  }

  @Override
  public PageResultS<AutoStatisticsVO> findByData(Integer autostatisticsType, Integer autostatisticsState) {
    // 构造分页参数
    Pageable pageable = PageRequest.of(0, 99999);

    try {
      // 调用 Repository 方法进行分页查询
      Page<AutoStatistics> evaluationLevels = autoStatisticsRepository
          .findByAutoStatistics(autostatisticsType, autostatisticsState, pageable);

      // 创建 VO 列表
      List<AutoStatisticsVO> voList = evaluationLevels.getContent().stream()
          .map(this::convertToVO)
          .collect(Collectors.toList());



      // 构造分页结果
      PageResultS<AutoStatisticsVO> pageResult = new PageResultS<>();
      pageResult.setTotalCount(evaluationLevels.getTotalElements());
      pageResult.setPageIndex(evaluationLevels.getNumber() + 1); // 页码从 1 开始
      pageResult.setPageSize((int)evaluationLevels.getTotalElements());
      pageResult.setList(voList);

      return pageResult;
    } catch (Exception e) {
      // 捕获异常并抛出自定义异常
      throw new RuntimeException("分页查询失败：" + e.getMessage(), e);
    }
  }

  @Override
  public void deleteById(Integer id) {
    autoStatisticsRepository.deleteById(id);
  }

  private AutoStatisticsVO convertToVO(AutoStatistics autoStatistics) {
    AutoStatisticsVO vo = new AutoStatisticsVO();
    BeanUtils.copyProperties(autoStatistics, vo);
    return vo;
  }
}
