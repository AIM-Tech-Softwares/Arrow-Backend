package com.aimtech.arrowcore.domain.business.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryDetailResponse {

    private Long internalId;

    private String countryName;

    private String isoCode;
}
