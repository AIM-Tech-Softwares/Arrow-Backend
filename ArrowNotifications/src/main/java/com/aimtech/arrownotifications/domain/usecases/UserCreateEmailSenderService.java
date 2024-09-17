package com.aimtech.arrownotifications.domain.usecases;

import com.aimtech.arrownotifications.domain.dto.request.UserCreateEmailSenderRequest;

public interface UserCreateEmailSenderService {
    void execute(UserCreateEmailSenderRequest dto);

}

