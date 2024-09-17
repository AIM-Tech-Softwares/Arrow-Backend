package com.aimtech.arrownotifications.core.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties("arrownotifications.app")
public class AppProperties {
    private final EmailSenderProps emailSender = new EmailSenderProps();


    @Getter
    @Setter
    public static class EmailSenderProps {
        private String from;
        private String friendlyName;
    }
}
