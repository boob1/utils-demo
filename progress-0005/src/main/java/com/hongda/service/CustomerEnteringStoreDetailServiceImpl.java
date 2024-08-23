package com.hongda.service;

import com.wp.domain.report.provider.common.constants.Constant;
import com.wp.domain.report.provider.common.util.DateUtils;
import com.wp.domain.report.provider.common.util.WhaleAuthUtil;
import com.wp.domain.report.provider.pojo.domain.CustomerEnteringStoreDayDetailDO;
import com.wp.domain.report.provider.pojo.domain.CustomerEnteringStoreHourDetailDO;
import com.wp.domain.report.provider.pojo.domain.JobProgressDO;
import com.wp.domain.report.provider.pojo.dto.CustomerFlowDetailsDTO;
import com.wp.domain.report.provider.pojo.dto.WhaleAuthResultData;
import com.wp.domain.report.provider.service.JobProgressService;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author lyb
 * @Date 2024/8/22 17:48
 */
@Slf4j
@Service
public class CustomerEnteringStoreDetailServiceImpl implements CustomerEnteringStoreDetailService {

  @Autowired
  private JobProgressService jobProgressService;

  @Autowired
  private CustomerEnteringStoreHourDetailService hourDetailService;

  @Autowired
  private CustomerEnteringStoreDayDetailService customerEnteringStoreDayDetailService;

  @Override
  public String saveCustomerEnteringStoreDayDetail() {
    JobProgressDO progressDO = jobProgressService.findByServiceName(
        Constant.CUSTOMER_ENTER_DAY_DETAIL);

    // 为空创建进度数据
    if (progressDO == null) {
      progressDO = jobProgressService.saveJobProgress(
          DateUtils.parseDateTime(Constant.CUSTOMER_ENTER_START_TIME),
          Constant.CUSTOMER_ENTER_DAY_DETAIL);
    }
    Date lastScanTime = progressDO.getLastScanTime();
    // 当前时间和开始时间相聚的时间
    long dayCount = DateUtils.getRemainDay(lastScanTime, new Date());

    if (dayCount == 0L) {
      log.info("已经是最新数据");
      return "已经是最新数据";
    }
    for (int i = 0; i < dayCount; i++) {
      Date recordTime = DateUtils.addDay(lastScanTime, i + 1);
      // 开始时间
      Date startTime = DateUtils.getEndOfDay(DateUtils.addDay(recordTime, -2));
      // 结束时间
      Date endTime = DateUtils.getEndOfDay(DateUtils.addDay(recordTime, -1));
      Date startOfDay = DateUtils.getStartOfDay(endTime);
      int pageSize = 100;//默认100
      boolean hasNextPage = false;
      String cursor = null;
      int totalNum = 0;

      // 拉取之前先删除当前天的历史数据
      customerEnteringStoreDayDetailService.removeByCompanyIdAndCurrentDay(
          progressDO.getCompanyId(), startOfDay);

      try {
        do {
          // 拉取第三方进店客流数据
          WhaleAuthResultData whaleAuthResultData = getCustomerFlow(progressDO.getCompanyId(),
              startTime.getTime(), endTime.getTime(), cursor, pageSize);

          cursor = whaleAuthResultData.getNext_cursor();
          List<CustomerFlowDetailsDTO> list = whaleAuthResultData.getList();
          if (list.size() == 0) {
            break;
          }

          // 类型转化
          JobProgressDO finalProgressDO = progressDO;
          List<CustomerEnteringStoreDayDetailDO> dayDetailDOList = list.stream().map(t -> {
            CustomerEnteringStoreDayDetailDO dayDetailDO = new CustomerEnteringStoreDayDetailDO();
            BeanUtils.copyProperties(t, dayDetailDO);
            dayDetailDO.setEnterpriseId(finalProgressDO.getCompanyId());
            dayDetailDO.setCurrentDay(startOfDay);
            return dayDetailDO;
          }).collect(Collectors.toList());

          // 保存入表
          customerEnteringStoreDayDetailService.saveBatch(dayDetailDOList);
          hasNextPage = list.size() == pageSize;
          totalNum += list.size();
        } while (hasNextPage);
        //同步开放平台订单
        log.info("【按天同步进店客流量】开始时间：{}---结束时间{}：{}，公司{}-- -- 同步{}条......",
            startTime,
            endTime, progressDO.getCompanyId(), totalNum);

        // 更新进度表
        progressDO.setLastScanTime(recordTime);
        jobProgressService.updateLastScanTime(progressDO);
      } catch (Exception e) {
       // log.error("【按天同步进店客流量】异常！{}", e.getMessage());
        return e.getMessage();
      }

    }
    return "执行完成!";
  }

  @Override
  public String saveCustomerEnteringStoreHourDetail() {
    JobProgressDO progressDO = jobProgressService.findByServiceName(
        Constant.CUSTOMER_ENTER_HOUR_DETAIL);

    // 拉取天数据，第一次，只拉取当天的就可以了
    Date currentDate = new Date();
    //昨天的23点59分59秒作为开始时间

    Date lastTime = DateUtils.getEndOfDay(DateUtils.addDay(currentDate, -1));
    if (progressDO == null) {
      progressDO = jobProgressService.saveJobProgress(lastTime,
          Constant.CUSTOMER_ENTER_HOUR_DETAIL);
    }
    // 当前时间取整：
    Date startTime = progressDO.getLastScanTime();
    // 按小时只拉取当天的数据，如果间隔太大，开始时间取昨天23点59分59秒
    if (startTime.getTime() < lastTime.getTime()) {
      startTime = lastTime;
    }
    Date endTime = DateUtils.getStartOfHour(currentDate);
    double betweenHours = DateUtils.getBetweenHours(endTime, startTime);

    //如果小于1小时，则表示已经是最新数据
    if (betweenHours < 1.0) {
      log.info("已经是最新数据");
      return "已经是最新数据";
    }
    // 如果结束时间是0小时，0分，0秒的话，修改成23点59分59秒
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(endTime);
    if (calendar.get(Calendar.HOUR_OF_DAY) == 0 && calendar.get(Calendar.MINUTE) == 0
        && calendar.get(Calendar.SECOND) == 0) {
      endTime = DateUtils.getEndOfDay(DateUtils.addDay(endTime, -1));
    }

    // 如果开始时间是0小时，0分，0秒的话，修改成23点59分59秒
    Calendar calendarStart = Calendar.getInstance();
    calendarStart.setTime(startTime);
    if (calendarStart.get(Calendar.HOUR_OF_DAY) == 0 && calendarStart.get(Calendar.MINUTE) == 0
        && calendarStart.get(Calendar.SECOND) == 0) {
      startTime = DateUtils.getEndOfDay(DateUtils.addDay(startTime, -1));
    }


    int pageSize = 100;//默认100
    boolean hasNextPage = false;
    String cursor = null;
    int totalNum = 0;

    try {
      do {
        // 拉取第三方进店客流数据
        WhaleAuthResultData whaleAuthResultData = getCustomerFlow(progressDO.getCompanyId(),
            startTime.getTime(), endTime.getTime(), cursor, pageSize);

        cursor = whaleAuthResultData.getNext_cursor();
        List<CustomerFlowDetailsDTO> list = whaleAuthResultData.getList();
        if (list.size() == 0) {
          break;
        }

        // 类型转化
        JobProgressDO finalProgressDO = progressDO;
        Date finalEndTime = endTime;
        List<CustomerEnteringStoreHourDetailDO> dayDetailDOList = list.stream().map(t -> {
          CustomerEnteringStoreHourDetailDO dayDetailDO = new CustomerEnteringStoreHourDetailDO();
          BeanUtils.copyProperties(t, dayDetailDO);
          dayDetailDO.setEnterpriseId(finalProgressDO.getCompanyId());
          dayDetailDO.setCurrentDay(DateUtils.getStartOfDay(finalEndTime));
          return dayDetailDO;
        }).collect(Collectors.toList());

        // 保存入表
        hourDetailService.saveBatch(dayDetailDOList);
        hasNextPage = list.size() == pageSize;
        totalNum += list.size();
      } while (hasNextPage);
      //同步开放平台订单
      log.info("【按小时同步进店客流量】开始时间：{}---结束时间{}：{}，公司{}-- -- 同步{}条......",
          startTime,
          endTime, progressDO.getCompanyId(), totalNum);

      // 更新进度表
      progressDO.setLastScanTime(endTime);
      jobProgressService.updateLastScanTime(progressDO);
    } catch (Exception e) {
      log.error("【按天同步进店客流量】异常！{}", e.getMessage());
      return e.getMessage();
    }
    return null;

  }

  @Override
  public WhaleAuthResultData getCustomerFlow(String CompanyId, Long startTime, Long endTime,
      String cursor, int limit) {
    return WhaleAuthUtil.getCustomerFlow(CompanyId, startTime, endTime, cursor, limit);
  }
}
