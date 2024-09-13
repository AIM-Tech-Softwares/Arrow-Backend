package com.aimtech.arrowcore.domain.business.mappers.admin;

import com.aimtech.arrowcore.domain.business.dto.requests.admin.UserRegisterRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.admin.UserRegisterResponse;
import com.aimtech.arrowcore.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {



    @Mapping(target = "password", ignore = true)
    @Mapping(target = "profiles", ignore = true)
    @Mapping(target = "lastLogin", ignore = true)
    @Mapping(target = "internalId", ignore = true)
    @Mapping(target = "externalId", ignore = true)
    @Mapping(target = "isFirstLogin", ignore = true)
    @Mapping(target = "businessGroup", ignore = true)
    @Mapping(target = "isAccountLocked", ignore = true)
    @Mapping(target = "isAccountExpired", ignore = true)
    @Mapping(target = "isPasswordExpired", ignore = true)
    @Mapping(target = "lastFailedLoginTime", ignore = true)
    @Mapping(target = "failedLoginAttempts", ignore = true)
    @Mapping(target = "lastPasswordChangeDate", ignore = true)
    User toUser(UserRegisterRequest dto);

    UserRegisterResponse toResponse(User user);
}
