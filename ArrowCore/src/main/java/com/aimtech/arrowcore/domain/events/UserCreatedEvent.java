package com.aimtech.arrowcore.domain.events;

import com.aimtech.arrowcore.infrastructure.messaging.message.UserCreateEmailSenderMessage;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserCreatedEvent extends ApplicationEvent {

    private final UserCreateEmailSenderMessage userCreateEmailSenderMessage;

    public UserCreatedEvent(Object source, UserCreateEmailSenderMessage userCreateEmailSenderMessage) {
        super(source);
        this.userCreateEmailSenderMessage = userCreateEmailSenderMessage;
    }
}
