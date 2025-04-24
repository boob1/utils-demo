package com.hongda.springbootlyb.service;

import com.hongda.springbootlyb.pojo.dto.AutoStatisticsDTO;
import com.hongda.springbootlyb.pojo.page.PageResultS;
import com.hongda.springbootlyb.pojo.vo.AutoStatisticsVO;
import utils.TreeNode;

public interface IAutoStatisticsService {

  /**
   * 保存数据
   * @param autoStatisticsDTO
   * @return
   */
  AutoStatisticsVO addautoStatistics(AutoStatisticsDTO autoStatisticsDTO);

  /**
   * 查询数据
   * @param autostatisticsType
   * @param autostatisticsState
   * @return
   */
  PageResultS<TreeNode> findByData(Integer autostatisticsType, Integer autostatisticsState);

  /**
   * 根据id删除数据
   * @param id
   */
  void deleteById(Integer id);

  /**
   * 根据id查询数据
   * @param id
   * @return
   */
  AutoStatisticsVO findById(Integer id);
}
