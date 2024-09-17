package com.aimtech.arrownotifications.infrastructure.messaging.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserCreateNotificationConsumer {

    @KafkaListener(
            topics = "create-user-topic",
            groupId = "create-user-consumer-1"
    )
    public void execute(String message) {
        System.out.println(message);
    }
}
