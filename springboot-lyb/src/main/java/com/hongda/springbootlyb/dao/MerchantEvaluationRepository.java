package com.hongda.springbootlyb.dao;

import com.hongda.springbootlyb.pojo.MerchantEvaluation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantEvaluationRepository  extends CrudRepository<MerchantEvaluation,Integer> {

}
