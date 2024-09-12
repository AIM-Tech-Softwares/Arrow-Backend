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
public class CitySummaryResponse {

    @Schema(description = "Internal identifier of the city", example = "12345")
    private Long internalId;

    @Schema(description = "Name of the city", example = "New York")
    private String cityName;

    @Schema(description = "IBGE of the city", example = "10152458")
    private String cityCode;

    @Schema(description = "Status indicating if the city is active", example = "true")
    private Boolean isActive;

    @Schema(description = "Identifier of the state to which the city belongs", example = "6789")
    private Long stateId;

    @Schema(description = "Code of the state to which the city belongs", example = "NY")
    private String stateCode;
}