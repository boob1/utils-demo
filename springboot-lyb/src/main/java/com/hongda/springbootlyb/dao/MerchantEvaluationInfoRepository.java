package com.hongda.springbootlyb.dao;

import com.hongda.springbootlyb.pojo.MerchantEvaluationInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantEvaluationInfoRepository  extends CrudRepository<MerchantEvaluationInfo,Integer> {

}
