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
public class StateDetailResponse {

    @Schema(description = "Internal identifier of the state", example = "1")
    private Long internalId;

    @Schema(description = "Name of the state", example = "New York")
    private String stateName;

    @Schema(description = "Code of the state", example = "NY")
    private String stateCode;

    @Schema(description = "Status indicating if the state is active", example = "true")
    private Boolean isActive;

    @Schema(description = "ISO code of the country the state belongs to", example = "US")
    private String countryIsoCode;
}
