package com.aimtech.arrowcore.domain.events;

import com.aimtech.arrowcore.infrastructure.messaging.message.PasswordRecoveryNotificationMessage;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class RecoveryUserPasswordEvent extends ApplicationEvent {

    private final PasswordRecoveryNotificationMessage message;

    public RecoveryUserPasswordEvent(Object source, PasswordRecoveryNotificationMessage message) {
        super(source);
        this.message = message;
    }
}
