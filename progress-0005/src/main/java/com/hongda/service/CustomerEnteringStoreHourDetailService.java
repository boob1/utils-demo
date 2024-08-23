package com.hongda.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wp.domain.report.provider.pojo.domain.CustomerEnteringStoreHourDetailDO;
import java.util.Date;

public interface CustomerEnteringStoreHourDetailService extends IService<CustomerEnteringStoreHourDetailDO> {
  void removeByCompanyIdAndCurrentDay(String enterpriseId, Date startOfDay);
}
