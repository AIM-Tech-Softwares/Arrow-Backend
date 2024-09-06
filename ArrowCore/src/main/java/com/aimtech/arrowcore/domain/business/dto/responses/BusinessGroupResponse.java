package com.aimtech.arrowcore.domain.business.dto.responses;

import com.aimtech.arrowcore.core.utils.IdGenerator;
import jakarta.persistence.Column;
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
