package com.aimtech.arrowcore.domain.business.mappers.admin;

import com.aimtech.arrowcore.domain.business.dto.requests.admin.UserRegisterRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.admin.UserDetailResponse;
import com.aimtech.arrowcore.domain.business.dto.responses.admin.UserRegisterResponse;
import com.aimtech.arrowcore.domain.business.dto.responses.admin.UserSummaryResponse;
import com.aimtech.arrowcore.domain.entities.Profile;
import com.aimtech.arrowcore.domain.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

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


    @Mapping(
            target = "profilesId",
            expression = "java(mapProfilesId(entity))"
    )
    @Mapping(
            target = "profilesName",
            expression = "java(mapProfilesName(entity))"
    )
    @Mapping(
            target = "tenantDomain",
            expression = "java(entity.getBusinessGroup().getTenantDomain())"
    )
    UserDetailResponse toDetailResponse(User entity);

    UserSummaryResponse toSummaryResponse(User entity);

    default List<String> mapProfilesName(User entity) {
        return entity.getProfiles().stream()
                .map(Profile::getProfileName)
                .collect(Collectors.toList());
    }

    default List<Long> mapProfilesId(User entity) {
        return entity.getProfiles().stream()
                .map(Profile::getInternalId)
                .collect(Collectors.toList());
    }
}
