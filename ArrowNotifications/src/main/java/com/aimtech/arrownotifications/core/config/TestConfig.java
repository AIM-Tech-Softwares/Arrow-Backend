package com.aimtech.arrownotifications.core.config;

import com.aimtech.arrownotifications.domain.usecases.UserCreateEmailSenderService;
import com.aimtech.arrownotifications.infrastructure.usecases.MockUserCreateEmailSenderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {

    @Bean
    public UserCreateEmailSenderService emailService() {
        return new MockUserCreateEmailSenderServiceImpl();
    }
}
