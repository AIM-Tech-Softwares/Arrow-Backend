package com.aimtech.arrowcore.domain.business.dto.responses.admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessGroupResponse {
    private Long internalId;
    private String externalId;
    private String publicName;
    private String tenantDomain;
    private String schemaName;
}
