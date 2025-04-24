package com.hongda.springbootlyb.dao;

import com.hongda.springbootlyb.pojo.EvaluationRules;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationRulesRepository extends CrudRepository<EvaluationRules, Integer> {

  @Query("SELECT el FROM EvaluationRules el WHERE el.EVALUATION_DEPARTMENT_ID in :evaluationDepartmentIds and el.EVALUATIONRULES_STATE=:evaluationrulesState")
  Page<EvaluationRules> findList(Integer evaluationrulesState, List<Integer> evaluationDepartmentIds,
      Pageable pageable);
  @Query("SELECT el FROM EvaluationRules el WHERE el.EVALUATION_DEPARTMENT_ID in :evaluationDepartmentIds "
      + "and el.EVALUATIONRULES_STATE=:evaluationrulesState and el.OWNERUNIT_ID=:ownerunitId and el.PROVINCE_CODE=:provinceCode")
  Page<EvaluationRules> findEditData(Integer evaluationrulesState, List<Integer> evaluationDepartmentIds, Integer ownerunitId, Integer provinceCode, Pageable pageable);
}
