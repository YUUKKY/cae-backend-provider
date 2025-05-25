package com.demo.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderDataDo implements Serializable {
    private String id;
    private Long timestamp;
    private Integer quantity;
    private String status;
    private Integer price;
    private String type;

    public OrderDataDo(String id, Long timestamp, Integer quantity, String status, Integer price, String type) {
        this.id = id;
        this.timestamp = timestamp;
        this.quantity = quantity;
        this.status = status;
        this.price = price;
        this.type = type;
    }
}