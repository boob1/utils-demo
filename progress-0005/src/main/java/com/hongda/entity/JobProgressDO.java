package com.hongda.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
* 定时器进度表
* @TableName job_progress
*/
@Data
@TableName(value ="job_progress")
public class JobProgressDO implements Serializable {

    /**
    * 主键
    */
    @TableId
    private Long id;
    /**
    * 创建时间
    */
    @TableField(value = "created_time")
    private Date createdTime;
    /**
    * 更新时间
    */
    @TableField(value = "updated_time")
    private Date updatedTime;
    /**
    * 公司ID
    */
    @TableField(value = "company_id")
    private String companyId;
    /**
    * 定时服务名称
    */
    @TableField(value = "service_name")
    private String serviceName;
    /**
    * 上一次服务处理的最后一条数据的主键id
    */
    @TableField(value = "last_id")
    private String lastId;
    /**
    * 上一次任务扫描时间
    */
    @TableField(value = "last_scan_time")
    private Date lastScanTime;
    /**
    * 前置时间
    */
    @TableField(value = "time_section")
    private Integer timeSection;



}
