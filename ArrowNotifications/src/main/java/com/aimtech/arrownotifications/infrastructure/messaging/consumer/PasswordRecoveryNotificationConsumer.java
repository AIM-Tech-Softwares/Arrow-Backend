package com.aimtech.arrownotifications.infrastructure.messaging.consumer;

import com.aimtech.arrownotifications.core.properties.AppProperties;
import com.aimtech.arrownotifications.domain.usecases.PasswordRecoveryNotificationService;
import com.aimtech.arrownotifications.infrastructure.messaging.message.PasswordRecoveryNotificationMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordRecoveryNotificationConsumer {
    private final PasswordRecoveryNotificationService passwordRecoveryNotificationService;
    private final AppProperties appProperties;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = "#{appProperties.getKafka.getTopics.getPasswordUserRecovery}",
            groupId = "#{appProperties.getKafka.getGroupId}"
    )
    public void execute(String message) {
        PasswordRecoveryNotificationMessage senderMessage = convert(message);
        passwordRecoveryNotificationService.execute(senderMessage);
    }

    private PasswordRecoveryNotificationMessage convert(String message) {
        try {
            return objectMapper.readValue(
                    message,
                    PasswordRecoveryNotificationMessage.class
            );
        } catch (Exception ex) {
            throw new RuntimeException("Failed to convert message: " + message);
        }
    }
}
