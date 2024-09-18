package com.aimtech.arrowcore.domain.listeners;

import com.aimtech.arrowcore.domain.events.UserCreatedEvent;
import com.aimtech.arrowcore.infrastructure.messaging.producer.UserCreateNotificationProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserCreatedListener implements ApplicationListener<UserCreatedEvent> {

    private final UserCreateNotificationProducer userCreateNotificationProducer;

    @Override
    public void onApplicationEvent(UserCreatedEvent event) {
        handleEvent(event);
    }

    private void handleEvent(UserCreatedEvent event) {
        try {
            userCreateNotificationProducer.execute(event.getUserCreateEmailSenderMessage());
        } catch (Exception e) {
            throw new RuntimeException("Failed to handle UserCreatedEvent", e);
        }
    }
}
