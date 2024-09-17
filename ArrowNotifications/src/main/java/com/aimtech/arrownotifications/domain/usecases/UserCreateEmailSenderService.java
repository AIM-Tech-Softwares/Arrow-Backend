package com.aimtech.arrownotifications.domain.usecases;

import com.aimtech.arrownotifications.infrastructure.messaging.message.UserCreateEmailSenderMessage;

public interface UserCreateEmailSenderService {

    void execute(UserCreateEmailSenderMessage dto);

}

