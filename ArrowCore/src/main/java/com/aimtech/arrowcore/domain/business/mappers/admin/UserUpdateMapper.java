package com.aimtech.arrowcore.domain.business.mappers.admin;

import com.aimtech.arrowcore.domain.business.dto.requests.admin.UserUpdateRequest;
import com.aimtech.arrowcore.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserUpdateMapper {

    @Mapping(target = "username", ignore = true)
    @Mapping(target = "profiles", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "lastLogin", ignore = true)
    @Mapping(target = "externalId", ignore = true)
    @Mapping(target = "internalId", ignore = true)
    @Mapping(target = "isFirstLogin", ignore = true)
    @Mapping(target = "businessGroup", ignore = true)
    @Mapping(target = "isAccountLocked", ignore = true)
    @Mapping(target = "isAccountExpired", ignore = true)
    @Mapping(target = "isPasswordExpired", ignore = true)
    @Mapping(target = "failedLoginAttempts", ignore = true)
    @Mapping(target = "lastFailedLoginTime", ignore = true)
    @Mapping(target = "lastPasswordChangeDate", ignore = true)
    void updateUser(UserUpdateRequest dto, @MappingTarget User user);
}
