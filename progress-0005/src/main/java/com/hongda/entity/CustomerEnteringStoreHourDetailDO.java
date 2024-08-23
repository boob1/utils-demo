package com.hongda.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
* 进店客流小时明细表
* @TableName customer_entering_store_hour_detail
*/
@Data
@TableName(value ="customer_entering_store_hour_detail")
public class CustomerEnteringStoreHourDetailDO implements Serializable {
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
    @TableField( value = "updated_time")
    private Date updatedTime;

    /**
     * 哪天
     */
    @TableField(value = "current_day")
    private Date currentDay;

    /**
     * 源创建时间
     */
    @TableField(value = "source_created_time")
    private Date sourceCreatedTime;
    /**
     * 年月日小时的整体显示，秒级时间戳
     */
    @TableField( value = "time_to_hour")
    private Long timeToHour;
    /**
     * 年
     */
    @TableField( value = "year")
    private String year;
    /**
     * 月
     */
    @TableField( value = "month")
    private String month;
    /**
     * 日
     */
    @TableField( value = "day")
    private String day;
    /**
     * 星期
     */
    @TableField( value = "day_in_week")
    private String dayInWeek;
    /**
     * 小时
     */
    @TableField( value = "hour")
    private String hour;
    /**
     * 天气ID
     */
    @TableField( value = "weather_cate_id")
    private String weatherCateId;
    /**
     * 天气名称
     */
    @TableField( value = "weather_cate_name")
    private String weatherCateName;
    /**
     * 气温ID
     */
    @TableField( value = "temperature_id")
    private String temperatureId;
    /**
     * 气温
     */
    @TableField( value = "temperature")
    private String temperature;
    /**
     * 营销活动
     */
    @TableField( value = "campaign")
    private String campaign;
    /**
     * 节假日判断0: 不是；1: 是
     */
    @TableField( value = "is_holiday")
    private Integer isHoliday;
    /**
     * 节假日名
     */
    @TableField( value = "holiday_name")
    private String holidayName;
    /**
     * 节假日ID
     */
    @TableField( value = "holiday_id")
    private String holidayId;
    /**
     * 大区
     */
    @TableField( value = "region")
    private String region;
    /**
     * 省份
     */
    @TableField( value = "province")
    private String province;
    /**
     * 城市
     */
    @TableField( value = "city")
    private String city;
    /**
     * 区域
     */
    @TableField( value = "district")
    private String district;
    /**
     * 公司ID
     */
    @TableField( value = "company_id")
    private String companyId;
    /**
     * 我们自己公司ID
     */
    @TableField( value = "enterprise_id")
    private String enterpriseId;
    /**
     * 公司名
     */
    @TableField( value = "company_name")
    private String companyName;

    /**
     * 店铺ID
     */
    @TableField( value = "shop_id")
    private String shopId;
    /**
     * 店铺名称
     */
    @TableField( value = "shop_name")
    private String shopName;
    /**
     * 店铺编号
     */
    @TableField( value = "shop_code")
    private String shopCode;
    /**
     * 设备组ID
     */
    @TableField( value = "group_id")
    private String groupId;
    /**
     * 设备组名称
     */
    @TableField( value = "group_name")
    private String groupName;
    /**
     * 设备SN
     */
    @TableField( value = "device_sn")
    private String deviceSn;
    /**
     * 设备标签
     */
    @TableField( value = "device_tag")
    private String deviceTag;
    /**
     * 门号
     */
    @TableField( value = "gate_id")
    private String gateId;
    /**
     * 性别
     */
    @TableField( value = "gender")
    private String gender;
    /**
     * 年龄层次
     */
    @TableField( value = "age_level")
    private String ageLevel;
    /**
     * 年代
     */
    @TableField( value = "age_decade")
    private String ageDecade;
    /**
     * 舒适度
     */
    @TableField( value = "comfort")
    private String comfort;
    /**
     * 表类型
     */
    @TableField( value = "table_type")
    private String tableType;
    /**
     * 过店数
     */
    @TableField( value = "count_passby")
    private Long countPassby;
    /**
     * 关注数
     */
    @TableField( value = "count_impression")
    private Long countImpression;
    /**
     * 进店数
     */
    @TableField( value = "count_in")
    private Long countIn;
    /**
     * 出店数
     */
    @TableField( value = "count_out")
    private Long countOut;
    /**
     * ID重复数-顾客部分
     */
    @TableField( value = "reid_dup_count_in")
    private Long reidDupCountIn;
    /**
     * ID重复数-店员
     */
    @TableField( value = "extra_staff_dup_count_in")
    private Long extraStaffDupCountIn;
    /**
     * ID重复数-外卖快递
     */
    @TableField( value = "extra_takeout_dup_count_in")
    private Long extraTakeoutDupCountIn;
    /**
     * ID重复数-异常顾客
     */
    @TableField( value = "extra_abnormal_dup_count_in")
    private Long extraAbnormalDupCountIn;


}
