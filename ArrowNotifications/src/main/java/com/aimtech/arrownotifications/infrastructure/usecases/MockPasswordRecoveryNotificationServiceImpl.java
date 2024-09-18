package com.aimtech.arrownotifications.infrastructure.usecases;

import com.aimtech.arrownotifications.domain.usecases.PasswordRecoveryNotificationService;
import com.aimtech.arrownotifications.infrastructure.messaging.message.PasswordRecoveryNotificationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MockPasswordRecoveryNotificationServiceImpl implements PasswordRecoveryNotificationService {

    private final Logger LOG = LoggerFactory.getLogger(MockPasswordRecoveryNotificationServiceImpl.class);


    @Override
    public void execute(PasswordRecoveryNotificationMessage dto) {
        LOG.info("Email send to: {}", dto.getEmail());
        LOG.info("redirectURL: {}", dto.getRedirectUrl());
        LOG.info("Tenant Domain: {}", dto.getTenantDomain());
        LOG.info("Finish processing");
    }
}
