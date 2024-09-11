package com.aimtech.arrowcore.domain.business.mappers;

import com.aimtech.arrowcore.domain.business.dto.requests.StreetTypeUpdateRequest;
import com.aimtech.arrowcore.domain.entities.StreetType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StreetTypeUpdateMapper {

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "internalId", ignore = true)
    void updateStreetType(StreetTypeUpdateRequest dto, @MappingTarget StreetType entity);
}
