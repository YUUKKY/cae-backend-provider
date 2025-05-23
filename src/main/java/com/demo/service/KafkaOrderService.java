package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaOrderService {

    @Autowired
    private final KafkaTemplate<String, String> kafkaTemplate;

    public static final String TOPIC = "orders";

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
}