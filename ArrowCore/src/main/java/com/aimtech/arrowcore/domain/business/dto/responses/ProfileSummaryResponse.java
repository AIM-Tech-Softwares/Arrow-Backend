package com.aimtech.arrowcore.domain.business.dto.responses;

import com.aimtech.arrowcore.domain.business.dto.responses.admin.RoleSummaryResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileSummaryResponse {

    @Schema(
            description = "Unique external identifier of the profile.",
            example = "123e4567-e89b-12d3-a456-426614174000",
            requiredMode = Schema.RequiredMode.AUTO
    )
    private UUID externalId;

    @Schema(
            description = "Name of the profile.",
            example = "Admin Profile",
            requiredMode = Schema.RequiredMode.AUTO
    )
    private String profileName;

    @Schema(
            description = "Indicates whether the profile is currently active.",
            example = "true",
            requiredMode = Schema.RequiredMode.AUTO
    )
    private Boolean isActive;
}
