package com.aimtech.arrowcore.domain.business.mappers;

import com.aimtech.arrowcore.domain.business.dto.requests.StateUpdateRequest;
import com.aimtech.arrowcore.domain.entities.State;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface StateUpdateMapper {


    @Mapping(target = "cities", ignore = true)
    @Mapping(target = "country", ignore = true)
    @Mapping(target = "internalId", ignore = true)
    void updateState(StateUpdateRequest dto, @MappingTarget State entity);
}
