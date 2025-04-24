package com.hongda.springbootlyb.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hongda.springbootlyb.pojo.EvaluationRules;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EvaluationRulesMapper extends BaseMapper<EvaluationRules> {
  List<EvaluationRules> findEditData(Integer evaluationrulesState, List<Integer> evaluationDepartmentIds, Integer ownerunitId, Integer provinceCode);

}
