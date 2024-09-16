package com.aimtech.arrowcore.domain.business.dto.requests.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUpdateRequest {

    @Schema(
            description = "The name of the profile.",
            example = "Admin Profile",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String profileName;

    @Schema(
            description = "A brief description of the profile.",
            example = "This profile has all administrative privileges.",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String description;

    @Schema(
            description = "Indicates whether the profile is currently active.",
            example = "true",
            requiredMode = Schema.RequiredMode.AUTO
    )
    private Boolean isActive;

    @Schema(
            description = "A list of role IDs associated with the profile.",
            example = "[1, 2, 3]",
            requiredMode = Schema.RequiredMode.AUTO
    )
    private List<Long> roleIds;
}
