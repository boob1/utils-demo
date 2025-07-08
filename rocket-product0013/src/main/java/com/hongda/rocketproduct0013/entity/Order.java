package com.hongda.rocketproduct0013.entity;

import java.util.List;

public class Order {
    private Long orderId;
    private List<Product> products;

    public Order(Long orderId, List<Product> products) {
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
}
