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
public class CountryDetailResponse {

    @Schema(description = "Internal identifier of the country", example = "1")
    private Long internalId;

    @Schema(description = "Name of the country", example = "United States")
    private String countryName;

    @Schema(description = "ISO code of the country", example = "US")
    private String isoCode;

    @Schema(description = "Status indicating if the country is active", example = "true")
    private Boolean isActive;
}
