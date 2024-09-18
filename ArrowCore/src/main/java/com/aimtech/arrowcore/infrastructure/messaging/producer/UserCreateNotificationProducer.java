package com.aimtech.arrowcore.infrastructure.messaging.producer;

import com.aimtech.arrowcore.infrastructure.messaging.message.UserCreateEmailSenderMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCreateNotificationProducer {
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    private final String topic = "create-user-topic";


    public String execute(UserCreateEmailSenderMessage dto) throws JsonProcessingException {
        String message = objectMapper.writeValueAsString(dto);
        kafkaTemplate.send(topic, message);
        return "Sender to processing";
    }
}
