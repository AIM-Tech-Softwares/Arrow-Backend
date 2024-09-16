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
public class RoleSummaryResponse {

    @Schema(
            description = "Unique identifier of the role.",
            example = "1",
            requiredMode = Schema.RequiredMode.AUTO
    )
    private Long id;

    @Schema(
            description = "Name of the role.",
            example = "Admin",
            requiredMode = Schema.RequiredMode.AUTO
    )
    private String roleInterfaceName;

    @Schema(
            description = "Description of the role.",
            example = "Permission for administrative user.",
            requiredMode = Schema.RequiredMode.AUTO
    )
    private String roleInterfaceDescription;
}
