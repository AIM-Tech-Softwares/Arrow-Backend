package com.aimtech.arrowcore.domain.business.dto.responses.management;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StateDetailResponse {

    private Long internalId;

    private String stateName;

    private String stateCode;

    private Boolean isActive;

    private String countryIsoCode;
}
