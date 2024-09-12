package com.aimtech.arrowcore.domain.business.mappers.management;

import com.aimtech.arrowcore.domain.business.dto.requests.management.CountryCreateRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.management.CountryDetailResponse;
import com.aimtech.arrowcore.domain.entities.Country;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    @Mapping(target = "states", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "internalId", ignore = true)
    Country toEntity(CountryCreateRequest dto);

    CountryDetailResponse toDetailResponse(Country entity);
}
