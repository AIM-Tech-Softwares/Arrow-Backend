package com.aimtech.arrowcore.domain.business.dto.responses.management;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StreetTypeDetailResponse {

    @Schema(description = "Internal identifier of the street type", example = "1")
    private Long internalId;

    @Schema(description = "Name of the street type", example = "Avenue")
    private String streetTypeName;

    @Schema(description = "Abbreviation of the street type", example = "Ave")
    private String streetTypeAbbreviation;

    @Schema(description = "Status indicating if the street type is active", example = "true")
    private Boolean isActive;

    @Schema(description = "Timestamp when the street type was created", example = "2023-10-01T12:34:56Z")
    private OffsetDateTime createdAt;

    @Schema(description = "Timestamp when the street type was last updated", example = "2023-10-10T15:20:35Z")
    private OffsetDateTime updatedAt;
}
