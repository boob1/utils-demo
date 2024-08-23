package com.hongda.service;

import com.wp.domain.report.provider.pojo.dto.WhaleAuthResultData;

public interface CustomerEnteringStoreDetailService {

  /**
    * @Author lyb
    * @Description 按小时拉取数据保存
    * @Date 19:14 2024/8/22
    * @Param []
    * @return void
    **/

  String saveCustomerEnteringStoreDayDetail();

  /**
    * @Author lyb
    * @Description 按天拉取数据保存
    * @Date 19:14 2024/8/22
    * @Param []
    * @return void
    **/
  String saveCustomerEnteringStoreHourDetail();



  WhaleAuthResultData getCustomerFlow(String CompanyId,Long startTime,Long endTime,String cursor,int limit);

}
