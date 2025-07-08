package com.hongda.rocketconsumer0014.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

public class User implements java.io.Serializable{
    private Integer id;
    private String name;

    @JsonCreator
    public User(@JsonProperty("name") String name,
                @JsonProperty("id") Integer id) {
        this.name = name;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
