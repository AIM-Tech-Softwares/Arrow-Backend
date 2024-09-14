package com.aimtech.arrowcore.domain.business.mappers.admin;

import com.aimtech.arrowcore.domain.business.dto.requests.admin.ProfileUpdateRequest;
import com.aimtech.arrowcore.domain.entities.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProfileUpdateMapper {

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "internalId", ignore = true)
    @Mapping(target = "externalId", ignore = true)
    void updateProfile(ProfileUpdateRequest dto, @MappingTarget Profile entity);
}
