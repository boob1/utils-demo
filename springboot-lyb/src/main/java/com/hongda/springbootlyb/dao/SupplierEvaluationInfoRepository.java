package com.hongda.springbootlyb.dao;

import com.hongda.springbootlyb.pojo.SupplierEvaluationInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierEvaluationInfoRepository  extends CrudRepository<SupplierEvaluationInfo,Integer> {

  @Query("SELECT el FROM SupplierEvaluationInfo el WHERE el.SUPPLIEREVALUATION_ID in :supplierevaluationId")
  Page<SupplierEvaluationInfo> findInfoList(Integer supplierevaluationId, Pageable pageable);
}
