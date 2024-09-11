package com.aimtech.arrowcore.domain.business.dto.responses.management;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
