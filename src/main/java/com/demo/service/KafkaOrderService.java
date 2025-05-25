package com.demo.service;

import com.demo.model.OrderDataDo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Service
public class KafkaOrderService {

    @Autowired
    private final KafkaTemplate<String, String> kafkaTemplate;

    public static final String TOPIC = "orders";

    @Autowired
    private OrderDataService dataService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public KafkaOrderService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public boolean pushOrder(String orderJson) {
        try {
            kafkaTemplate.send(TOPIC, orderJson);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 每5秒自动生产并推送消息
    @Scheduled(fixedRate = 5000)
    public void produceMessage() {
        // 生成订单内容
        Map<String, Object> order = new HashMap<>();
        order.put("id", UUID.randomUUID().toString());
        order.put("timestamp", System.currentTimeMillis());
        order.put("quantity", 5);
        order.put("status", "NEW");
        order.put("price", 100 + new Random().nextInt(20));
        order.put("type", "customer");

        try {
            String orderJson = objectMapper.writeValueAsString(order);
            kafkaTemplate.send(TOPIC, orderJson);
            dataService.insert(new OrderDataDo((String) order.get("id"), (Long) order.get("timestamp"),
                    (Integer) order.get("quantity"), (String) order.get("status"), (Integer) order.get("price"), (String) order.get("type")));
        } catch (Exception e) {
            System.out.println("TESTING-ERROR: " + e);
        }
    }
}