package com.hongda.rocketconsumer0014.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Order {
    private Long orderId;
    private List<Product> products;

    @JsonCreator
    public Order(@JsonProperty("orderId") Long orderId,
                 @JsonProperty("products") List<Product> products) {
        this.orderId = orderId;
        this.products = products;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", products=" + products +
                '}';
    }
}
