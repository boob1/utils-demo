package com.hongda.springbootlyb.mapper;

import com.hongda.springbootlyb.pojo.AutoStatistics;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoStatisticsRepository extends CrudRepository<AutoStatistics,Integer> {

  @Query("SELECT el FROM AutoStatistics el WHERE el.AUTOSTATISTICS_TYPE = :autostatisticsType and el.AUTOSTATISTICS_STATE=:autostatisticsState")
  Page<AutoStatistics> findByAutoStatistics(Integer autostatisticsType, Integer autostatisticsState, Pageable pageable);
}
