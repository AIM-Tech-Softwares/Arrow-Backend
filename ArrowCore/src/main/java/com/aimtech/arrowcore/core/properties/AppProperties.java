package com.aimtech.arrowcore.core.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("arrowcore.app")
public class AppProperties {
    private final DefaultValues defaultValues = new DefaultValues();
    private final KafkaProps kafka = new KafkaProps();

    @Getter
    @Setter
    public static class DefaultValues {
        private String adminProfileName;
        private String adminProfileDescription;
        private String adminFirstName;
        private String adminLastName;
        private String adminUsername;
        private String adminPassword;
        private String defaultSchemaName;
        private Integer minimumPasswordLength;
        private final PasswordRecoveryToken recoveryToken = new PasswordRecoveryToken();
        private final LoginAttempts loginAttempts = new LoginAttempts();
    }

    @Getter
    @Setter
    public static class PasswordRecoveryToken {
        private Integer expiryInSeconds;
        private String redirectUri;
    }

    @Getter
    @Setter
    public static class LoginAttempts {
        private Integer maxAttempts;
        private Integer lockoutDurationInSeconds;
    }

    @Getter
    @Setter
    public static class KafkaProps {
        private String server;
        private Integer retries;
        private Integer requestTimeout;
        private String retentionMs;
        private String cleanupPolicy;
        private TopicsProps topics = new TopicsProps();
    }

    @Getter
    @Setter
    public static class TopicsProps {
        private String createUser;
    }
}
