package com.hongda.springbootlyb.dao;

import com.hongda.springbootlyb.pojo.EvaluationLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EvaluationLevelRepository extends JpaRepository<EvaluationLevel, Integer> {

  /**
   * 根据类型查询评价等级，并支持分页。
   *
   * @param evaluationLevelType 评价等级类型
   * @param pageable 分页参数
   * @return 包含符合条件的评价等级的分页结果
   */
  @Query("SELECT el FROM EvaluationLevel el WHERE el.EVALUATIONLEVEL_TYPE = :evaluationLevelType")
  Page<EvaluationLevel> findByEvaluationLevelType(
      @Param("evaluationLevelType") Integer evaluationLevelType,
      Pageable pageable);
}

