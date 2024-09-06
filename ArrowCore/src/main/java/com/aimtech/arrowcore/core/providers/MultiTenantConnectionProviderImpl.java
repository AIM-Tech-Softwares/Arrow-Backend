package com.aimtech.arrowcore.core.providers;

import lombok.RequiredArgsConstructor;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Component
@RequiredArgsConstructor
public class MultiTenantConnectionProviderImpl implements MultiTenantConnectionProvider {
    private static final Logger logger = LoggerFactory.getLogger(MultiTenantConnectionProviderImpl.class);
    private final DataSource dataSource;

    @Override
    public Connection getAnyConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public Connection getConnection(Object tenantIdentifier) throws SQLException {
        logger.info("Getting connection for tenant: {}", tenantIdentifier.toString().toLowerCase());
        Connection connection = getAnyConnection();
        try {
            connection.setSchema(tenantIdentifier.toString().toLowerCase());
            logger.info("Schema set to: {}", tenantIdentifier.toString().toLowerCase());
        } catch (SQLException e) {
            logger.error("Error setting schema for tenant {}: {}",
                    tenantIdentifier.toString().toLowerCase(), e.getMessage());
            throw new SQLException("Error setting schema: " + e.getMessage(), e);
        }
        return connection;
    }

    @Override
    public void releaseConnection(Object tenantIdentifier, Connection connection) throws SQLException {
        try {
            String DEFAULT_TENANT = "public";
            connection.setSchema(DEFAULT_TENANT);
            logger.info("Schema reset to default tenant: {}", DEFAULT_TENANT);
        } catch (SQLException e) {
            logger.error("Error resetting schema to default tenant: {}", e.getMessage());
            logger.error("Error resetting schema to default tenant: {}", e.getMessage());
        } finally {
            releaseAnyConnection(connection);
        }
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    public boolean isUnwrappableAs(Class aClass) {
        return aClass.isAssignableFrom(MultiTenantConnectionProvider.class);
    }

    @Override
    public <T> T unwrap(Class<T> aClass) {
        return null;
    }
}
