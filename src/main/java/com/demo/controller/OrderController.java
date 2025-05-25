package com.demo.controller;

import com.demo.model.OrderDataDo;
import com.demo.service.OrderDataService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.demo.service.KafkaOrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@RestController
@RequestMapping("/v1")
public class OrderController {
    @Autowired
    private OrderDataService dataService;

    @Value("${order_speed:1}")
    private int orderSpeed; // 读取环境变量，默认1

    @Autowired
    private KafkaOrderService kafkaOrderService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/orders")
    public ResponseEntity<Map<String, Object>> generateOrder() {
        // 生成订单内容
        Map<String, Object> order = new HashMap<>();
        order.put("id", UUID.randomUUID().toString());
        order.put("timestamp", System.currentTimeMillis());
        order.put("quantity", orderSpeed);
        order.put("status", "NEW");
        order.put("price", 100 + new Random().nextInt(20));
        order.put("type", "customer");

        try {
            String orderJson = objectMapper.writeValueAsString(order);
            boolean success = kafkaOrderService.pushOrder(orderJson);

            if (success) {
                dataService.insert(new OrderDataDo((String) order.get("id"), (Long) order.get("timestamp"),
                        (Integer) order.get("quantity"), (String) order.get("status"), (Integer) order.get("price"), (String) order.get("type")));

                return ResponseEntity.ok(order);
            } else {
                return ResponseEntity.status(500).body(new HashMap<String, Object>() {{
                    put("error", "Failed to push order to Kafka");
                }});
            }
        } catch (Exception e) {
            System.out.println("TESTING-ERROR: " + e);
            return ResponseEntity.status(500).body(new HashMap<String, Object>() {{
                put("error", "Serialization failed");
            }});
        }
    }
}