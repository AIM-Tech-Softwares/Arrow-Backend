package com.aimtech.arrowcore.domain.business.dto.requests.management;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StreetTypeCreateRequest {

    @Schema(
            description = "Name of the street type",
            example = "Avenue",
            requiredMode = Schema.RequiredMode.AUTO
    )
    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    private String streetTypeName;

    @Schema(
            description = "Abbreviation of the street type",
            example = "Ave",
            requiredMode = Schema.RequiredMode.AUTO
    )
    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    private String streetTypeAbbreviation;

    @Schema(
            description = "Indicates if the street type is active",
            example = "true",
            requiredMode = Schema.RequiredMode.AUTO
    )
    @NotNull(message = "{arrowcore.messages.errors.validation.NotNull}")
    private Boolean isActive;
}
