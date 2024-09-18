package com.aimtech.arrowcore.domain.listeners;

import com.aimtech.arrowcore.domain.events.RecoveryUserPasswordEvent;
import com.aimtech.arrowcore.infrastructure.messaging.producer.PasswordRecoveryNotificationProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecoveryUserPasswordListener implements ApplicationListener<RecoveryUserPasswordEvent> {

    private final PasswordRecoveryNotificationProducer producer;


    @Override
    public void onApplicationEvent(RecoveryUserPasswordEvent event) {
        handleEvent(event);
    }

    private void handleEvent(RecoveryUserPasswordEvent event) {
        try {
            producer.execute(event.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Failed to handle RecoveryUserPasswordEvent", e);
        }
    }
}
