package com.aimtech.arrowcore.domain.business.mappers;

import com.aimtech.arrowcore.domain.business.dto.requests.CityCreateRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.CityDetailResponse;
import com.aimtech.arrowcore.domain.business.dto.responses.CitySummaryResponse;
import com.aimtech.arrowcore.domain.entities.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CityMapper {

    @Mapping(target = "state", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "internalId", ignore = true)
    City toEntity(CityCreateRequest dto);

    @Mapping(
            target = "stateId",
            expression = "java(entity.getState().getInternalId())"
    )
    @Mapping(
            target = "stateCode",
            expression = "java(entity.getState().getStateCode())"
    )
    CityDetailResponse toDetailResponse(City entity);

    @Mapping(
            target = "stateId",
            expression = "java(entity.getState().getInternalId())"
    )
    @Mapping(
            target = "stateCode",
            expression = "java(entity.getState().getStateCode())"
    )
    CitySummaryResponse toSummaryResponse(City entity);
}
