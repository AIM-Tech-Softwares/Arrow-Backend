package com.aimtech.arrowcore.domain.business.dto.responses.management;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StreetTypeDetailResponse {

    private Long internalId;

    private String streetTypeName;

    private String streetTypeAbbreviation;

    private Boolean isActive;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;
}
