package com.aimtech.arrowcore.core.services;

import com.aimtech.arrowcore.core.enums.RoleEnum;
import com.aimtech.arrowcore.core.properties.AppProperties;
import com.aimtech.arrowcore.core.utils.IdGenerator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataInitializationService {
    private final AppProperties appProperties;

    @Transactional
    public void seedRolesPublic(JdbcTemplate jdbcTemplate) {
        try {
            List<String> existingRoles = jdbcTemplate.queryForList("SELECT authority FROM tb_role", String.class);
            List<String> desiredRoles = Arrays.stream(RoleEnum.values()).map(Enum::name).toList();

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

    private void insertProfile(JdbcTemplate jdbcTemplate, String scheme) {
        String profileName = this.appProperties.getDefaultValues().getAdminProfileName();
        String description = this.appProperties.getDefaultValues().getAdminProfileDescription();
        String externalId = IdGenerator.generateExternalId();
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
}
