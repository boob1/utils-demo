package com.hongda.springbootlyb.mapper;

import com.hongda.springbootlyb.pojo.Supplier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description
 * @Author lyb
 * @Date 2025/4/23 12:53
 */
@Repository
public interface SupplierRepository  extends CrudRepository<Supplier,Integer> {

}
