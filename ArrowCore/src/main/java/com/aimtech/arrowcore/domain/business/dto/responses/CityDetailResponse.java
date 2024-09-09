package com.aimtech.arrowcore.domain.business.dto.responses;

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

    private Long internalId;

    private String cityName;

    private String cityCode;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private Boolean isActive;

    private Long stateId;

    private String stateCode;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;
}
