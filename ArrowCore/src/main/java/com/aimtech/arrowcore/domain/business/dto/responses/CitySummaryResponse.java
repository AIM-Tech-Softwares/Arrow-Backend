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
public class CitySummaryResponse {

    private Long internalId;

    private String cityName;

    private String cityCode;

    private Boolean isActive;

    private Long stateId;

    private String stateCode;
}
