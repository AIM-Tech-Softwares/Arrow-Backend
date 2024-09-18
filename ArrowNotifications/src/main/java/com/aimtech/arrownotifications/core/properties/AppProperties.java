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
    private final KafkaProps kafka = new KafkaProps();

    @Getter
    @Setter
    public static class EmailSenderProps {
        private String from;
        private String friendlyName;
    }

    @Getter
    @Setter
    public static class KafkaProps {
        private String server;
        private Integer retries;
        private Integer requestTimeout;
        private String retentionMs;
        private String cleanupPolicy;
        private String groupId;
        private TopicsProps topics = new TopicsProps();
    }

    @Getter
    @Setter
    public static class TopicsProps {
        private String createUser;
        private String passwordUserRecovery;
    }
}
