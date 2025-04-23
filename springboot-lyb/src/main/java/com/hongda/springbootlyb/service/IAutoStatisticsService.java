package com.hongda.springbootlyb.service;

import com.hongda.springbootlyb.pojo.dto.AutoStatisticsDTO;
import com.hongda.springbootlyb.pojo.page.PagePara;
import com.hongda.springbootlyb.pojo.page.PageResultS;
import com.hongda.springbootlyb.pojo.vo.AutoStatisticsVO;

public interface IAutoStatisticsService {

  AutoStatisticsVO addautoStatistics(AutoStatisticsDTO autoStatisticsDTO);

  PageResultS<AutoStatisticsVO> findByData(Integer autostatisticsType, Integer autostatisticsState);

  void deleteById(Integer id);
}
