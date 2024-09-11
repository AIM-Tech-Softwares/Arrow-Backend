package com.aimtech.arrowcore.domain.business.mappers.management;

import com.aimtech.arrowcore.domain.business.dto.requests.management.StreetTypeCreateRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.management.StreetTypeDetailResponse;
import com.aimtech.arrowcore.domain.business.dto.responses.management.StreetTypeSummaryResponse;
import com.aimtech.arrowcore.domain.entities.StreetType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StreetTypeMapper {

    @Mapping(target = "internalId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    StreetType toEntity(StreetTypeCreateRequest dto);

    StreetTypeDetailResponse toDetailResponse(StreetType entity);

    StreetTypeSummaryResponse toSummaryResponse(StreetType entity);
}
