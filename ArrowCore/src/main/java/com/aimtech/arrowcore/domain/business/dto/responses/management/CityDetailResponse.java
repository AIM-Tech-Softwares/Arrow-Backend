package com.aimtech.arrowcore.domain.business.dto.responses.management;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityDetailResponse {

    @Schema(description = "Internal identifier of the city", example = "12345")
    private Long internalId;

    @Schema(description = "Name of the city", example = "New York")
    private String cityName;

    @Schema(description = "IBGE of the city", example = "10152458")
    private String cityCode;

    @Schema(description = "Latitude of the city", example = "40.712776")
    private BigDecimal latitude;

    @Schema(description = "Longitude of the city", example = "-74.005974")
    private BigDecimal longitude;

    @Schema(description = "Status indicating if the city is active", example = "true")
    private Boolean isActive;

    @Schema(description = "Identifier of the state to which the city belongs", example = "6789")
    private Long stateId;

    @Schema(description = "Code of the state to which the city belongs", example = "NY")
    private String stateCode;

    @Schema(description = "Timestamp when the city details were created", example = "2023-10-01T12:34:56Z")
    private OffsetDateTime createdAt;

    @Schema(description = "Timestamp when the city details were last updated", example = "2023-10-10T15:20:35Z")
    private OffsetDateTime updatedAt;
}
