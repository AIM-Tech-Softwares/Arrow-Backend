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
    }
}
