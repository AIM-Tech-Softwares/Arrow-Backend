package com.aimtech.arrowcore.domain.business.mappers.management;

import com.aimtech.arrowcore.domain.business.dto.requests.management.CityUpdateRequest;
import com.aimtech.arrowcore.domain.entities.City;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CityUpdateMapper {

    @Mapping(target = "state", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "internalId", ignore = true)
    void updateCity(CityUpdateRequest dto, @MappingTarget City entity);
}
