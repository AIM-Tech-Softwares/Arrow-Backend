package com.aimtech.arrowcore.domain.business.dto.requests.management;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StreetTypeUpdateRequest {

    @Schema(description = "Name of the street type", example = "Avenue")
    private String streetTypeName;

    @Schema(description = "Abbreviation of the street type", example = "Ave")
    private String streetTypeAbbreviation;

    @Schema(description = "Indicates if the street type is active", example = "true")
    private Boolean isActive;
}
