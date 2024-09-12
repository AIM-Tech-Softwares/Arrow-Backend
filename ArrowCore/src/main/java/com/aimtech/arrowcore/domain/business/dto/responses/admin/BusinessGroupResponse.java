package com.aimtech.arrowcore.domain.business.dto.responses.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessGroupResponse {
    @Schema(description = "Internal ID of the business group", example = "1")
    private Long internalId;

    @Schema(description = "External ID of the business group", example = "4d565427-0a30-4e2a-a722-0ba90e59d01a")
    private String externalId;

    @Schema(description = "Public name of the business group", example = "Tech Group")
    private String publicName;

    @Schema(description = "Tenant domain associated with the business group", example = "techgroup.com")
    private String tenantDomain;

    @Schema(description = "Schema name associated with the business group", example = "techgroup_schema")
    private String schemaName;
}
