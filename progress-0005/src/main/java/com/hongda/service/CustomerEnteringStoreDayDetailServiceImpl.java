package com.hongda.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wp.domain.report.provider.mapper.db0.CustomerEnteringStoreDayDetailMapper;
import com.wp.domain.report.provider.pojo.domain.CustomerEnteringStoreDayDetailDO;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author lyb
 * @Date 2024/8/23 10:54
 */
@Service
public class CustomerEnteringStoreDayDetailServiceImpl  extends
    ServiceImpl<CustomerEnteringStoreDayDetailMapper, CustomerEnteringStoreDayDetailDO>  implements
    CustomerEnteringStoreDayDetailService {

  @Autowired
  private CustomerEnteringStoreDayDetailMapper customerEnteringStoreDayDetailMapper;

  @Override
  public void removeByCompanyIdAndCurrentDay(String enterpriseId, Date startOfDay) {
    CustomerEnteringStoreDayDetailDO detailDO = new CustomerEnteringStoreDayDetailDO();
    detailDO.setCurrentDay(startOfDay);
    detailDO.setEnterpriseId(enterpriseId);
    customerEnteringStoreDayDetailMapper.removeByCompanyIdAndCurrentDay(detailDO);
  }
}
