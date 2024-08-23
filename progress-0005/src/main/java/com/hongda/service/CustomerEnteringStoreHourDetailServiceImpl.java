package com.hongda.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wp.domain.report.provider.mapper.db0.CustomerEnteringStoreHourDetailMapper;
import com.wp.domain.report.provider.pojo.domain.CustomerEnteringStoreHourDetailDO;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author lyb
 * @Date 2024/8/23 10:55
 */
@Service
public class CustomerEnteringStoreHourDetailServiceImpl extends ServiceImpl<CustomerEnteringStoreHourDetailMapper, CustomerEnteringStoreHourDetailDO> implements
    CustomerEnteringStoreHourDetailService {
  @Autowired
  private CustomerEnteringStoreHourDetailMapper customerEnteringStoreHourDetailMapper;
  @Override
  public void removeByCompanyIdAndCurrentDay(String enterpriseId, Date startOfDay) {
    CustomerEnteringStoreHourDetailDO detailDO = new CustomerEnteringStoreHourDetailDO();
    detailDO.setCurrentDay(startOfDay);
    detailDO.setEnterpriseId(enterpriseId);
    customerEnteringStoreHourDetailMapper.removeByCompanyIdAndCurrentDay(detailDO);
  }

}
