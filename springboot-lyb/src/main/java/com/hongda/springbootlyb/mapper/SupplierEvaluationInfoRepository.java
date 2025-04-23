package com.hongda.springbootlyb.mapper;

import com.hongda.springbootlyb.pojo.SupplierEvaluationInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierEvaluationInfoRepository  extends CrudRepository<SupplierEvaluationInfo,Integer> {

}
