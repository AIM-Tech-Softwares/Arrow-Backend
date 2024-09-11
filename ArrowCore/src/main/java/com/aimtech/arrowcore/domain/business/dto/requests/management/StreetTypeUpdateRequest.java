package com.aimtech.arrowcore.domain.business.dto.requests.management;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StreetTypeUpdateRequest {

    private String streetTypeName;

    private String streetTypeAbbreviation;

    private Boolean isActive;
}
