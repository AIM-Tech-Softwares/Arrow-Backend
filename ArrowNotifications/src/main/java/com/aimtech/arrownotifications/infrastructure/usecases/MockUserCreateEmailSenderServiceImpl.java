package com.aimtech.arrownotifications.infrastructure.usecases;

import com.aimtech.arrownotifications.domain.dto.request.UserCreateEmailSenderRequest;
import com.aimtech.arrownotifications.domain.usecases.UserCreateEmailSenderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MockUserCreateEmailSenderServiceImpl implements UserCreateEmailSenderService {

    private final Logger LOG = LoggerFactory.getLogger(MockUserCreateEmailSenderServiceImpl.class);


    @Override
    public void execute(UserCreateEmailSenderRequest dto) {
        LOG.info("Email sent to: {}", dto.getUserEmail());
        LOG.info("Username is: {}", dto.getUsername());
        LOG.info("Password is: {}", dto.getPassword());
        LOG.info("Finish processing");
    }
}
