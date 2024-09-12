package com.aimtech.arrowcore.domain.business.dto.requests.management;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryUpdateRequest {

    @Schema(description = "Name of the country", example = "Brazil")
    private String countryName;

    @Schema(description = "ISO code of the country", example = "BRA")
    @Size(min = 3, max = 3, message = "{arrowcore.messages.errors.validation.Size}")
    private String isoCode;

    @Schema(description = "Indicates if the country is active", example = "true")
    private Boolean isActive;
}
