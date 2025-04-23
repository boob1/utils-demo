package com.hongda.springbootlyb.mapper;

import com.hongda.springbootlyb.pojo.EvaluationBill;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description
 * @Author lyb
 * @Date 2025/4/23 12:50
 */
@Repository
public interface EvaluationBillRepository  extends CrudRepository<EvaluationBill,Integer> {

}
