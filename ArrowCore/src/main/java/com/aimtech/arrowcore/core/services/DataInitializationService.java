package com.aimtech.arrowcore.core.services;

import com.aimtech.arrowcore.core.enums.RoleEnum;
import com.aimtech.arrowcore.core.properties.AppProperties;
import com.aimtech.arrowcore.core.utils.IdGenerator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DataInitializationService {
    private final AppProperties appProperties;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void seedRolesPublic(JdbcTemplate jdbcTemplate) {
        try {
            List<String> existingRoles = jdbcTemplate.queryForList("SELECT authority FROM tb_role", String.class);
            List<String> desiredRoles = Arrays.stream(RoleEnum.values()).map(RoleEnum::getRoleName).toList();

            List<String> rolesToInsert = new ArrayList<>(desiredRoles);
            rolesToInsert.removeAll(existingRoles);

            for (String role: rolesToInsert) {
                jdbcTemplate.update("INSERT INTO tb_role (authority) VALUES (?)", role);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void seedProfilePublic(JdbcTemplate jdbcTemplate) {
        try {
            String defaultSchemaName = this.appProperties.getDefaultValues().getDefaultSchemaName();
            insertProfile(jdbcTemplate, defaultSchemaName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void seedProfileAdminRoles(JdbcTemplate jdbcTemplate) {
        String defaultSchemaName = this.appProperties.getDefaultValues().getDefaultSchemaName();
        String profileName = this.appProperties.getDefaultValues().getAdminProfileName();
        try {
            Long adminProfileId = getAdminProfileId(jdbcTemplate, profileName, defaultSchemaName);

            if (adminProfileId != null) {
                List<Long> roleIds = getUnassociatedRoleIds(jdbcTemplate, adminProfileId, defaultSchemaName);
                insertProfileRoles(jdbcTemplate, adminProfileId, roleIds, defaultSchemaName);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void replicateRolesToSchemas(JdbcTemplate jdbcTemplate) {
        try {
            List<String> tenantSchemas = getTenantSchemas(jdbcTemplate);
            String defaultSchemaName = this.appProperties.getDefaultValues().getDefaultSchemaName();
            String publicSchemaQuery = String.format(
                    "SELECT authority, role_interface_description, role_interface_name FROM %s.tb_role",
                    defaultSchemaName
            );

            List<RoleData> publicRoles = jdbcTemplate.query(publicSchemaQuery, (rs, rowNum) ->
                    new RoleData(
                            rs.getString("authority"),
                            rs.getString("role_interface_description"),
                            rs.getString("role_interface_name")
                    )
            );

            for (String schema : tenantSchemas) {
                String existingRolesQuery = String.format(
                        "SELECT authority, role_interface_description, role_interface_name FROM %s.tb_role",
                        schema
                );
                List<RoleData> existingRoles = jdbcTemplate.query(existingRolesQuery, (rs, rowNum) ->
                        new RoleData(
                                rs.getString("authority"),
                                rs.getString("role_interface_description"),
                                rs.getString("role_interface_name")
                        )
                );

                for (RoleData publicRole : publicRoles) {
                    boolean roleExists = false;
                    for (RoleData existingRole : existingRoles) {
                        if (publicRole.getAuthority().equals(existingRole.getAuthority())) {
                            roleExists = true;
                            break;
                        }
                    }

                    if (roleExists) {
                        String updateQuery = String.format(
                                "UPDATE %s.tb_role SET role_interface_description = ?, role_interface_name = ? WHERE authority = ?",
                                schema
                        );
                        jdbcTemplate.update(
                                updateQuery,
                                publicRole.getRoleInterfaceDescription(),
                                publicRole.getRoleInterfaceName(),
                                publicRole.getAuthority()
                        );
                    } else {
                        String insertQuery = String.format(
                                "INSERT INTO %s.tb_role (authority, role_interface_description, role_interface_name) VALUES (?, ?, ?)",
                                schema
                        );
                        jdbcTemplate.update(
                                insertQuery,
                                publicRole.getAuthority(),
                                publicRole.getRoleInterfaceDescription(),
                                publicRole.getRoleInterfaceName()
                        );
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void seedProfileToSchemas(JdbcTemplate jdbcTemplate) {
        try {
            List<String> tenantSchemas = getTenantSchemas(jdbcTemplate);
            for (String schema : tenantSchemas) {
                insertProfile(jdbcTemplate, schema);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void seedProfileAdminRolesToSchemas(JdbcTemplate jdbcTemplate) {
        List<String> tenantSchemas = getTenantSchemas(jdbcTemplate);
        String profileName = this.appProperties.getDefaultValues().getAdminProfileName();

        for (String schema : tenantSchemas) {
            Long adminProfileId = getAdminProfileId(jdbcTemplate, profileName, schema);
            if (adminProfileId != null) {
                List<Long> roleIds = getUnassociatedRoleIds(jdbcTemplate, adminProfileId, schema);
                insertProfileRoles(jdbcTemplate, adminProfileId, roleIds, schema);
            }
        }
    }

    @Transactional
    public void replicateBusinessGroupToSchemas(JdbcTemplate jdbcTemplate) {
        try {
            List<String> tenantSchemas = getTenantSchemas(jdbcTemplate);
            for (String currentSchema : tenantSchemas) {
                BusinessGroupData businessGroupData = getBusinessGroupData(jdbcTemplate, currentSchema);

                if (businessGroupData != null) {
                    if (businessGroupExistsInSchema(jdbcTemplate, currentSchema)) {
                        updateBusinessGroupInSchema(jdbcTemplate, currentSchema, businessGroupData);
                    } else {
                        insertBusinessGroupInSchema(jdbcTemplate, currentSchema, businessGroupData);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void seedDefaultUser(JdbcTemplate jdbcTemplate) {
        try {
            String defaultSchemaName = this.appProperties.getDefaultValues().getDefaultSchemaName();
            BusinessGroupData businessGroupData = getBusinessGroupDataFromSchema(jdbcTemplate, defaultSchemaName);
            if (businessGroupData != null) {
                insertDefaultUser(jdbcTemplate, defaultSchemaName, businessGroupData.getInternalId());
            } else {
                throw new RuntimeException("businessGroup is null from schema: " + defaultSchemaName);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void seedDefaultUserToSchemas(JdbcTemplate jdbcTemplate) {
        try {
            List<String> tenantSchemas = getTenantSchemas(jdbcTemplate);
            for (String schema : tenantSchemas) {
                BusinessGroupData businessGroupData = getBusinessGroupDataFromSchema(jdbcTemplate, schema);
                if (businessGroupData != null) {
                    insertDefaultUser(jdbcTemplate, schema, businessGroupData.getInternalId());
                } else {
                    throw new RuntimeException("businessGroup is null from schema: " + schema);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void associateAdminUserWithAdminProfile(JdbcTemplate jdbcTemplate) {
        try {
            String defaultSchemaName = appProperties.getDefaultValues().getDefaultSchemaName();
            String defaultAdminUsername = appProperties.getDefaultValues().getAdminUsername();
            String defaultAdminProfileName = appProperties.getDefaultValues().getAdminProfileName();
            Long adminUserId = getUserIdByUsername(jdbcTemplate, defaultAdminUsername, defaultSchemaName);
            Long adminProfileId = getProfileIdByName(jdbcTemplate, defaultAdminProfileName, defaultSchemaName);

            if (adminUserId != null && adminProfileId != null) {
                insertUserProfile(jdbcTemplate, adminUserId, adminProfileId, defaultSchemaName);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void associateAdminUserWithAdminProfileToSchemas(JdbcTemplate jdbcTemplate) {
        try {
            List<String> tenantSchemas = getTenantSchemas(jdbcTemplate);
            String defaultAdminUsername = appProperties.getDefaultValues().getAdminUsername();
            String defaultAdminProfileName = appProperties.getDefaultValues().getAdminProfileName();
            for (String schema : tenantSchemas) {
                Long adminUserId = getUserIdByUsername(jdbcTemplate, defaultAdminUsername, schema);
                Long adminProfileId = getProfileIdByName(jdbcTemplate, defaultAdminProfileName, schema);

                if (adminUserId != null && adminProfileId != null) {
                    insertUserProfile(jdbcTemplate, adminUserId, adminProfileId, schema);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void insertProfile(JdbcTemplate jdbcTemplate, String scheme) {
        String profileName = this.appProperties.getDefaultValues().getAdminProfileName();
        String description = this.appProperties.getDefaultValues().getAdminProfileDescription();
        UUID externalId = IdGenerator.generateExternalId();
        String query = String.format(
                "INSERT INTO %s.tb_profile (profile_name, description, external_id, is_active) VALUES (?, ?, ?, ?) ON CONFLICT (profile_name) DO NOTHING",
                scheme
        );
        jdbcTemplate.update(query, profileName, description, externalId, true);
    }

    private Long getAdminProfileId(JdbcTemplate jdbcTemplate, String profileName, String schema) {
        String query = String.format(
                "SELECT internal_id FROM %s.tb_profile WHERE profile_name = ?",
                schema
        );
        try {
            return jdbcTemplate.queryForObject(query, Long.class, profileName);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private List<Long> getUnassociatedRoleIds(JdbcTemplate jdbcTemplate, Long adminProfileId, String schema) {
        String query = String.format("""
            SELECT r.internal_id FROM %s.tb_role r
            WHERE NOT EXISTS (
                SELECT 1 FROM %s.tb_profile_role pr
                WHERE pr.role_id = r.internal_id AND pr.profile_id = ?
            )
            """,
            schema, schema
        );
        return jdbcTemplate.query(query, (rs, rowNum) -> rs.getLong("internal_id"), adminProfileId);
    }

    private void insertProfileRoles(JdbcTemplate jdbcTemplate, Long adminProfileId, List<Long> roleIds, String schema) {
        String insertQuery = String.format(
                "INSERT INTO %s.tb_profile_role (profile_id, role_id) VALUES (?, ?)",
                schema
        );
        for (Long roleId : roleIds) {
            jdbcTemplate.update(insertQuery, adminProfileId, roleId);
        }
    }

    private List<String> getTenantSchemas(JdbcTemplate jdbcTemplate) {
        String query = "SELECT schema_name FROM information_schema.schemata WHERE schema_name NOT IN ('information_schema', 'pg_catalog', 'public', 'pg_toast')";
        return jdbcTemplate.queryForList(query, String.class);
    }

    private BusinessGroupData getBusinessGroupData(JdbcTemplate jdbcTemplate, String schema) {
        String defaultSchemaName = this.appProperties.getDefaultValues().getDefaultSchemaName();
        String query = String.format(
                "SELECT internal_id, external_id, public_name, tenant_domain, schema_name FROM %s.tb_business_group WHERE schema_name = ?",
                defaultSchemaName
        );

        List<BusinessGroupData> results = jdbcTemplate.query(query, ps -> ps.setString(1, schema),
                (rs, rowNum) ->
                        new BusinessGroupData(
                                rs.getString("internal_id"),
                                rs.getString("external_id"),
                                rs.getString("public_name"),
                                rs.getString("tenant_domain"),
                                rs.getString("schema_name")
                        )
        );
        return results.isEmpty() ? null : results.getFirst();
    }

    private BusinessGroupData getBusinessGroupDataFromSchema(JdbcTemplate jdbcTemplate, String schema) {
        String query = String.format(
                "SELECT internal_id, external_id, public_name, tenant_domain, schema_name FROM %s.tb_business_group WHERE schema_name = ?",
                schema
        );

        List<BusinessGroupData> results = jdbcTemplate.query(query, ps -> ps.setString(1, schema),
                (rs, rowNum) ->
                        new BusinessGroupData(
                                rs.getString("internal_id"),
                                rs.getString("external_id"),
                                rs.getString("public_name"),
                                rs.getString("tenant_domain"),
                                rs.getString("schema_name")
                        )
        );
        return results.isEmpty() ? null : results.getFirst();
    }

    private boolean businessGroupExistsInSchema(JdbcTemplate jdbcTemplate, String schemaName) {
        String query = String.format(
                "SELECT COUNT(*) FROM %s.tb_business_group WHERE schema_name = ?",
                schemaName
        );
        Integer count = jdbcTemplate.queryForObject(query, Integer.class, schemaName);
        return count != null && count > 0;
    }

    private void updateBusinessGroupInSchema(JdbcTemplate jdbcTemplate, String schema, BusinessGroupData businessGroupData) {
        String updateQuery = String.format(
                "UPDATE %s.tb_business_group SET public_name = ?, tenant_domain = ?, schema_name = ? WHERE external_id = ?",
                schema
        );
        jdbcTemplate.update(
                updateQuery,
                businessGroupData.getPublicName(),
                businessGroupData.getTenantDomain(),
                businessGroupData.getSchemaName(),
                businessGroupData.getExternalId()
        );
    }

    private void insertBusinessGroupInSchema(JdbcTemplate jdbcTemplate, String schema, BusinessGroupData businessGroupData) {
        String insertQuery = String.format(
                "INSERT INTO %s.tb_business_group (external_id, public_name, tenant_domain, schema_name) VALUES (?, ?, ?, ?)",
                schema
        );
        jdbcTemplate.update(
                insertQuery,
                businessGroupData.getExternalId(),
                businessGroupData.getPublicName(),
                businessGroupData.getTenantDomain(),
                businessGroupData.getSchemaName()
        );
    }

    private void insertDefaultUser(JdbcTemplate jdbcTemplate, String schema, String businessGroupInternalId) {
        UUID externalId = IdGenerator.generateExternalId();
        String firstName = this.appProperties.getDefaultValues().getAdminFirstName();
        String lastName = this.appProperties.getDefaultValues().getAdminLastName();
        String username = this.appProperties.getDefaultValues().getAdminUsername();
        String password = passwordEncoder.encode(this.appProperties.getDefaultValues().getAdminPassword());
        boolean isActive = true;
        boolean isFirstLogin = true;

        String query = String.format(
                """ 
                    INSERT INTO %s.tb_user
                    (external_id, first_name, last_name, username, password, is_active, is_first_login, business_group)
                    VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                    ON CONFLICT (username) DO NOTHING
                """,
                schema
        );
        jdbcTemplate.update(
                query, externalId,
                firstName, lastName,
                username, password,
                isActive, isFirstLogin,
                Long.parseLong(businessGroupInternalId)
        );
    }

    private Long getUserIdByUsername(JdbcTemplate jdbcTemplate, String username, String schema) {
        String query = String.format("SELECT internal_id FROM %s.tb_user WHERE username = ?", schema);
        try {
            return jdbcTemplate.queryForObject(query, Long.class, username);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException(
                    String.format(
                            "Username: '%s' not found in schema: '%s'",
                            username, schema
                    )
            );
        }
    }

    private Long getProfileIdByName(JdbcTemplate jdbcTemplate, String profileName, String schema) {
        String query = String.format("SELECT internal_id FROM %s.tb_profile WHERE profile_name = ?", schema);
        try {
            return jdbcTemplate.queryForObject(query, Long.class, profileName);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException(
                    String.format(
                            "Profile: '%s' not found in schema: '%s'",
                            profileName, schema
                    )
            );
        }
    }

    private void insertUserProfile(JdbcTemplate jdbcTemplate, Long userId, Long profileId, String schema) {
        String query = String.format("INSERT INTO %s.tb_user_profile (user_id, profile_id) VALUES (?, ?) ON CONFLICT DO NOTHING", schema);
        jdbcTemplate.update(query, userId, profileId);
    }

    @Getter
    private static class RoleData {
        private final String authority;
        private final String roleInterfaceDescription;
        private final String roleInterfaceName;

        public RoleData(String authority, String roleInterfaceDescription, String roleInterfaceName) {
            this.authority = authority;
            this.roleInterfaceDescription = roleInterfaceDescription;
            this.roleInterfaceName = roleInterfaceName;
        }
    }

    @Getter
    private static class BusinessGroupData {
        private final String internalId;
        private final String externalId;
        private final String publicName;
        private final String tenantDomain;
        private final String schemaName;

        public BusinessGroupData(String internalId, String externalId, String publicName, String tenantDomain, String schemaName) {
            this.internalId = internalId;
            this.externalId = externalId;
            this.publicName = publicName;
            this.tenantDomain = tenantDomain;
            this.schemaName = schemaName;
        }

        public BusinessGroupData(String externalId, String publicName, String tenantDomain, String schemaName) {
            this.internalId = null;
            this.externalId = externalId;
            this.publicName = publicName;
            this.tenantDomain = tenantDomain;
            this.schemaName = schemaName;
        }
    }
}
