package com.aimtech.arrowcore.core.config;

import com.aimtech.arrowcore.core.services.DataInitializationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@RequiredArgsConstructor
public class DataInitializationConfig {
    private final DataInitializationService dataInitializationService;

    @Bean
    public ApplicationRunner dataInitializer(JdbcTemplate jdbcTemplate) {
        return args -> {
            this.dataInitializationService.seedRolesPublic(jdbcTemplate);
            this.dataInitializationService.seedProfilePublic(jdbcTemplate);
            this.dataInitializationService.seedProfileAdminRoles(jdbcTemplate);
            this.dataInitializationService.seedDefaultUser(jdbcTemplate);
            this.dataInitializationService.associateAdminUserWithAdminProfile(jdbcTemplate);

            this.dataInitializationService.replicateRolesToSchemas(jdbcTemplate);
            this.dataInitializationService.seedProfileToSchemas(jdbcTemplate);
            this.dataInitializationService.seedProfileAdminRolesToSchemas(jdbcTemplate);
            this.dataInitializationService.replicateBusinessGroupToSchemas(jdbcTemplate);
            this.dataInitializationService.seedDefaultUserToSchemas(jdbcTemplate);
            this.dataInitializationService.associateAdminUserWithAdminProfileToSchemas(jdbcTemplate);
        };
    }

}
