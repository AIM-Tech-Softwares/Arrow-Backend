package com.aimtech.arrowcore.domain.business.dto.requests.management;

import com.aimtech.arrowcore.core.annotation.ValidLatitude;
import com.aimtech.arrowcore.core.annotation.ValidLongitude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityCreateRequest {

    @Schema(
            description = "Name of the city",
            example = "New York",
            requiredMode = Schema.RequiredMode.AUTO
    )
    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    private String cityName;

    @Schema(
            description = "Code of the city",
            example = "NYC",
            requiredMode = Schema.RequiredMode.AUTO
    )
    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    private String cityCode;

    @Schema(
            description = "Latitude of the city",
            example = "40.7128",
            requiredMode = Schema.RequiredMode.AUTO
    )
    @NotNull(message = "{arrowcore.messages.errors.validation.NotNull}")
    @ValidLatitude(message = "{arrowcore.errors.validation.custom.ValidLatitude}")
    private BigDecimal latitude;

    @Schema(
            description = "Longitude of the city",
            example = "-74.0060",
            requiredMode = Schema.RequiredMode.AUTO
    )
    @NotNull(message = "{arrowcore.messages.errors.validation.NotNull}")
    @ValidLongitude(message = "{arrowcore.errors.validation.custom.ValidLongitude}")
    private BigDecimal longitude;

    @Schema(
            description = "Indicates if the city is active",
            example = "true",
            requiredMode = Schema.RequiredMode.AUTO
    )
    @NotNull(message = "{arrowcore.messages.errors.validation.NotNull}")
    private Boolean isActive;

    @Schema(
            description = "ID of the state to which the city belongs",
            example = "1",
            requiredMode = Schema.RequiredMode.AUTO
    )
    @NotNull(message = "{arrowcore.messages.errors.validation.NotNull}")
    private Long stateId;
}
