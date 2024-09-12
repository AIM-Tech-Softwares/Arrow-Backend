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
public class TaxRegimeDetailResponse {

    @Schema(description = "Internal identifier of the entity", example = "123")
    private Long internalId;

    @Schema(description = "Name of the tax regime", example = "Simples Nacional")
    private String taxRegimeName;

    @Schema(description = "Status indicating if the entity is active", example = "true")
    private Boolean isActive;
}
