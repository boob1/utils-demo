package com.hongda.springbootlyb.service.impl;

import com.hongda.springbootlyb.mapper.AutoStatisticsRepository;
import com.hongda.springbootlyb.mapper.EvaluationLevelRepository;
import com.hongda.springbootlyb.pojo.AutoStatistics;
import com.hongda.springbootlyb.pojo.EvaluationLevel;
import com.hongda.springbootlyb.pojo.dto.AutoStatisticsDTO;
import com.hongda.springbootlyb.pojo.page.PagePara;
import com.hongda.springbootlyb.pojo.page.PageResultS;
import com.hongda.springbootlyb.pojo.vo.AutoStatisticsVO;
import com.hongda.springbootlyb.pojo.vo.EvaluationLevelVO;
import com.hongda.springbootlyb.service.IAutoStatisticsService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import utils.TreeBuilder;
import utils.TreeNode;

/**
 * @Description 处理自定义统计归口表业务
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
  public PageResultS<TreeNode> findByData(Integer autostatisticsType, Integer autostatisticsState) {
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

      // 子节点
      List<Map<String, Object>> dataList = voList.stream()
          .filter(Objects::nonNull) // 过滤掉 null 元素，避免 NullPointerException
          .map(vo -> {
            Map<String, Object> beanMap = BeanMap.create(vo);
            // 如果需要线程安全的 Map，可以将 beanMap 转换为 ConcurrentHashMap
            return beanMap;
          })
          .collect(Collectors.toList());

      // 构建树
      List<TreeNode> tree = TreeBuilder.buildTree(dataList);


      // 构造分页结果
      PageResultS<TreeNode> pageResult = new PageResultS<>();
      pageResult.setTotalCount(evaluationLevels.getTotalElements());
      pageResult.setPageIndex(evaluationLevels.getNumber() + 1); // 页码从 1 开始
      pageResult.setPageSize((int)evaluationLevels.getTotalElements());
      pageResult.setList(tree);

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

  @Override
  public AutoStatisticsVO findById(Integer id) {
    Optional<AutoStatistics> repository = autoStatisticsRepository.findById(id);
    if (repository.isPresent()) {
      AutoStatistics level = repository.get();
      AutoStatisticsVO levelVO = new AutoStatisticsVO();
      BeanUtils.copyProperties(level, levelVO);
      return levelVO;
    }
    return null;
  }

  private AutoStatisticsVO convertToVO(AutoStatistics autoStatistics) {
    AutoStatisticsVO vo = new AutoStatisticsVO();
    BeanUtils.copyProperties(autoStatistics, vo);
    return vo;
  }
}
