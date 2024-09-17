package com.aimtech.arrownotifications.core.config;

import com.aimtech.arrownotifications.core.properties.AppProperties;
import com.aimtech.arrownotifications.domain.usecases.UserCreateEmailSenderService;
import com.aimtech.arrownotifications.infrastructure.usecases.SendGridUserCreateEmailSenderServiceImpl;
import com.sendgrid.SendGrid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.thymeleaf.TemplateEngine;

@Configuration
@Profile("prod")
@RequiredArgsConstructor
public class ProdConfig {
    private final AppProperties appProperties;
    private final SendGrid sendGrid;
    private final TemplateEngine templateEngine;

    @Bean
    public UserCreateEmailSenderService userCreateEmailSenderService() {
        return new SendGridUserCreateEmailSenderServiceImpl(appProperties, sendGrid, templateEngine);
    }
}
