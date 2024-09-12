package com.aimtech.arrowcore.domain.business.dto.requests.management;

import com.aimtech.arrowcore.core.annotation.ValidLatitude;
import com.aimtech.arrowcore.core.annotation.ValidLongitude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityUpdateRequest {

    @Schema(description = "Name of the city", example = "New York")
    private String cityName;

    @Schema(description = "Code of the city", example = "NYC")
    private String cityCode;

    @Schema(description = "Latitude of the city", example = "40.7128")
    @ValidLatitude(message = "{arrowcore.errors.validation.custom.ValidLatitude}")
    private BigDecimal latitude;

    @Schema(description = "Longitude of the city", example = "-74.0060")
    @ValidLongitude(message = "{arrowcore.errors.validation.custom.ValidLongitude}")
    private BigDecimal longitude;

    @Schema(description = "Indicates if the city is active", example = "true")
    private Boolean isActive;

    @Schema(description = "ID of the state to which the city belongs", example = "1")
    private Long stateId;
}
