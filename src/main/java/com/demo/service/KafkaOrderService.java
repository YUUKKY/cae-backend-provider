package com.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaOrderService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${order.kafka.topic:orders}")
    private String topic;

    public KafkaOrderService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public boolean pushOrder(String orderJson) {
        try {
            kafkaTemplate.send(topic, orderJson);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}