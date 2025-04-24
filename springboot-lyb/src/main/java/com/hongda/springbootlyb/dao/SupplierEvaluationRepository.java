package com.hongda.springbootlyb.dao;

import com.hongda.springbootlyb.pojo.SupplierEvaluation;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierEvaluationRepository extends CrudRepository<SupplierEvaluation, Integer> {

  @Query("SELECT el FROM SupplierEvaluation el WHERE el.SUPPLIER_ID in :supplierDepartmentIds")
  Page<SupplierEvaluation> findList(List<Integer> supplierDepartmentIds, Pageable pageable);
}
