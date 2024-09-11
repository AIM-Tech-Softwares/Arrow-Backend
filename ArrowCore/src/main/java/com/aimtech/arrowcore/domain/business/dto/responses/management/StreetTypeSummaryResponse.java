package com.aimtech.arrowcore.domain.business.dto.responses.management;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StreetTypeSummaryResponse {

    private Long internalId;

    private String streetTypeName;

    private String streetTypeAbbreviation;

    private Boolean isActive;
}
