package com.hongda.springbootlyb.mapper;

import com.hongda.springbootlyb.pojo.EvaluationRules;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationRulesRepository  extends CrudRepository<EvaluationRules,Integer> {

}
