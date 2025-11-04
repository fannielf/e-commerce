package com.buy01.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserEventService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static final String TOPIC = "user-events";

    public void sendUserDeletedEvent(String userId) {
        kafkaTemplate.send(TOPIC, "DELETE_USER:" + userId);
    }
}
