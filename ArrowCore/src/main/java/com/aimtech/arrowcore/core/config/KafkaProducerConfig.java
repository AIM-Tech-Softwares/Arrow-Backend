package com.aimtech.arrowcore.core.config;

import com.aimtech.arrowcore.core.properties.AppProperties;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig {
    private final AppProperties appProperties;

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        String server = appProperties.getKafka().getServer();
        Integer retries = appProperties.getKafka().getRetries();
        Integer timeout = appProperties.getKafka().getRequestTimeout();

        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.ACKS_CONFIG, "all");
        configProps.put(ProducerConfig.RETRIES_CONFIG, retries);
        configProps.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, timeout);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public NewTopic createUserTopic() {
        String topic = appProperties.getKafka().getTopics().getCreateUser();
        String retention = appProperties.getKafka().getRetentionMs();
        String cleanup = appProperties.getKafka().getCleanupPolicy();

        return TopicBuilder
                .name(topic)
                .partitions(1)
                .replicas(1)
                .config("retention.ms", retention)
                .config("cleanup.policy", cleanup)
                .build();
    }
}
