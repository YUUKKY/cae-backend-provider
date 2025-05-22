package com.demo.service.impl;

import com.demo.mapper.OrderDataMapper;
import com.demo.model.OrderDataDo;
import com.demo.service.OrderDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDataServiceImpl implements OrderDataService {
    @Autowired
    private OrderDataMapper dataMapper;

    @Override
    public List<OrderDataDo> getAllOrders() {
        return dataMapper.getAllOrders();
    }

    @Override
    public void insert(OrderDataDo orderData) {
        dataMapper.insert(orderData.getId(), orderData.getStatus(), orderData.getQuantity(), orderData.getTimestamp(), orderData.getPrice());
    }
}
