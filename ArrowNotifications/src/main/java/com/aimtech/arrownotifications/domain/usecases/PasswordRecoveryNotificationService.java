package com.aimtech.arrownotifications.domain.usecases;

import com.aimtech.arrownotifications.infrastructure.messaging.message.PasswordRecoveryNotificationMessage;

public interface PasswordRecoveryNotificationService {

    void execute(PasswordRecoveryNotificationMessage dto);

}

