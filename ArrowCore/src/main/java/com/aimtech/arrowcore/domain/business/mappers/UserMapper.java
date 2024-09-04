package com.aimtech.arrowcore.domain.business.mappers;

import com.aimtech.arrowcore.domain.business.dto.requests.UserRegisterRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.UserRegisterResponse;
import com.aimtech.arrowcore.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {


    @Mapping(target = "profiles", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "lastLogin", ignore = true)
    @Mapping(target = "internalId", ignore = true)
    @Mapping(target = "externalId", ignore = true)
    @Mapping(target = "businessGroup", ignore = true)
    User toUser(UserRegisterRequest dto);

    UserRegisterResponse toResponse(User user);
}
