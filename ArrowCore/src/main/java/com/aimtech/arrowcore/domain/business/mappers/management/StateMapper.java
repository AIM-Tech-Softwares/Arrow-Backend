package com.aimtech.arrowcore.domain.business.mappers.management;

import com.aimtech.arrowcore.domain.business.dto.requests.management.StateCreateRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.management.StateDetailResponse;
import com.aimtech.arrowcore.domain.entities.State;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StateMapper {

    @Mapping(target = "cities", ignore = true)
    @Mapping(target = "country", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "internalId", ignore = true)
    State toEntity(StateCreateRequest dto);

    @Mapping(
            target = "countryIsoCode",
            expression = "java(entity.getCountry().getIsoCode())"
    )
    StateDetailResponse toDetailResponse(State entity);
}
