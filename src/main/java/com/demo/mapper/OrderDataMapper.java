package com.demo.mapper;

import com.demo.model.OrderDataDo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDataMapper {
    List<OrderDataDo> getAllOrders();

    void insert(String id, String status, Integer quantity, Long timestamp, Integer price, String type);
}
