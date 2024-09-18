package com.aimtech.arrownotifications.infrastructure.messaging.consumer;

import com.aimtech.arrownotifications.core.properties.AppProperties;
import com.aimtech.arrownotifications.domain.usecases.UserCreateEmailSenderService;
import com.aimtech.arrownotifications.infrastructure.messaging.message.UserCreateEmailSenderMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCreateNotificationConsumer {

    private final UserCreateEmailSenderService userCreateEmailSenderService;
    private final AppProperties appProperties;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = "#{appProperties.getKafka.getTopics.getCreateUser}",
            groupId = "#{appProperties.getKafka.getGroupId}"
    )
    public void execute(String message) {
        UserCreateEmailSenderMessage senderMessage = convert(message);
        userCreateEmailSenderService.execute(senderMessage);
    }

    private UserCreateEmailSenderMessage convert(String message) {
        try {
            return objectMapper.readValue(
                    message,
                    UserCreateEmailSenderMessage.class
            );
        } catch (Exception ex) {
            throw new RuntimeException("Failed to convert message: " + message);
        }
    }
}
