package com.aimtech.arrowcore.core.config;

import com.aimtech.arrowcore.domain.entities.BusinessGroup;
import com.aimtech.arrowcore.domain.repository.BusinessGroupRepository;
import lombok.RequiredArgsConstructor;
import org.flywaydb.core.Flyway;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("SqlResolve")
@Configuration
@RequiredArgsConstructor
public class FlywayConfig {
    private final DataSource dataSource;


    @Bean
    public FlywayMigrationStrategy flywayMigrationStrategy() {
        return flyway -> {
            Flyway.configure()
                    .dataSource(dataSource)
                    .schemas("public")
                    .load()
                    .migrate();

            List<String> tenants = getSchemaList();
            for (String tenant : tenants) {
                Flyway.configure()
                        .dataSource(dataSource)
                        .schemas(tenant)
                        .load()
                        .migrate();
            }
        };
    }

    private List<String> getSchemaList() {
        List<String> schemas = new ArrayList<>();
        String query = "SELECT schema_name FROM information_schema.schemata WHERE schema_name NOT IN ('information_schema', 'pg_catalog', 'public', 'pg_toast')";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                schemas.add(rs.getString("schema_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return schemas;
    }
}
