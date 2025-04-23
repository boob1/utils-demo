package com.hongda.springbootlyb.mapper;

import com.hongda.springbootlyb.pojo.SupplierEvaluation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierEvaluationRepository  extends CrudRepository<SupplierEvaluation,Integer> {

}
