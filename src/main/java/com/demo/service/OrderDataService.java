package com.demo.service;


import com.demo.model.OrderDataDo;
import java.util.List;

public interface OrderDataService {


    List<OrderDataDo> getAllOrders();

    void insert(OrderDataDo orderData);

}
