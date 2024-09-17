package com.aimtech.arrowcore.infrastructure.messaging.producer;

import com.aimtech.arrowcore.core.properties.AppProperties;
import com.aimtech.arrowcore.infrastructure.messaging.message.PasswordRecoveryNotificationMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordRecoveryNotificationProducer {
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final AppProperties appProperties;

    public void execute(PasswordRecoveryNotificationMessage dto) throws JsonProcessingException {
        String topic = appProperties.getKafka().getTopics().getPasswordUserRecovery();
        String message = objectMapper.writeValueAsString(dto);
        kafkaTemplate.send(topic, message);
    }
}
