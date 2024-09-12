package com.aimtech.arrowcore.domain.business.dto.responses.management;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StreetTypeSummaryResponse {

    @Schema(description = "Internal identifier of the street type", example = "1")
    private Long internalId;

    @Schema(description = "Name of the street type", example = "Avenue")
    private String streetTypeName;

    @Schema(description = "Abbreviation of the street type", example = "Ave")
    private String streetTypeAbbreviation;

    @Schema(description = "Status indicating if the street type is active", example = "true")
    private Boolean isActive;
}
