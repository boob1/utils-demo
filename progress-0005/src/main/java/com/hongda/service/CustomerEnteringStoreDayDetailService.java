package com.hongda.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wp.domain.report.provider.pojo.domain.CustomerEnteringStoreDayDetailDO;
import java.util.Date;

public interface CustomerEnteringStoreDayDetailService extends IService<CustomerEnteringStoreDayDetailDO> {

  void removeByCompanyIdAndCurrentDay(String enterpriseId, Date startOfDay);
}
