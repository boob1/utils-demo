package com.bobo.flowablespringboot18.entity.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ 审批列表查询结果
 */

public class TaskVO {
    private String id;
    private String day;
    private String name;

    public TaskVO() {
    }

    public TaskVO(String id, String day, String name) {
        this.id = id;
        this.day = day;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
