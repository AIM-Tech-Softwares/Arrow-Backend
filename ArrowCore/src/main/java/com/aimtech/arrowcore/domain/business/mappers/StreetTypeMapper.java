package com.aimtech.arrowcore.domain.business.mappers;

import com.aimtech.arrowcore.domain.business.dto.requests.StreetTypeCreateRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.StreetTypeDetailResponse;
import com.aimtech.arrowcore.domain.business.dto.responses.StreetTypeSummaryResponse;
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
