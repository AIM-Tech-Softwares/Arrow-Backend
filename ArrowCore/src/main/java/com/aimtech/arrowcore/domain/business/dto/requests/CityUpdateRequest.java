package com.aimtech.arrowcore.domain.business.dto.requests;

import com.aimtech.arrowcore.core.annotation.ValidLatitude;
import com.aimtech.arrowcore.core.annotation.ValidLongitude;
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
public class CityUpdateRequest {

    private String cityName;

    private String cityCode;

    @ValidLatitude(message = "{arrowcore.errors.validation.custom.ValidLatitude}")
    private BigDecimal latitude;

    @ValidLongitude(message = "{arrowcore.errors.validation.custom.ValidLongitude}")
    private BigDecimal longitude;

    private Boolean isActive;

    private Long stateId;
}
