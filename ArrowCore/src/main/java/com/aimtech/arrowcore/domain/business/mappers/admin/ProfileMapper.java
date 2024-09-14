package com.aimtech.arrowcore.domain.business.mappers.admin;

import com.aimtech.arrowcore.domain.business.dto.requests.admin.ProfileCreateRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.ProfileSummaryResponse;
import com.aimtech.arrowcore.domain.business.dto.responses.admin.ProfileDetailResponse;
import com.aimtech.arrowcore.domain.business.dto.responses.admin.RoleSummaryResponse;
import com.aimtech.arrowcore.domain.entities.Profile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "internalId", ignore = true)
    @Mapping(target = "externalId", ignore = true)
    Profile toEntity(ProfileCreateRequest dto);

    @Mapping(
            target = "roles",
            expression = "java(mapRoleSummary(entity))"
    )
    ProfileDetailResponse toDetailResponse(Profile entity);

    ProfileSummaryResponse toSummaryResponse(Profile entity);


    default List<RoleSummaryResponse> mapRoleSummary(Profile entity) {
        return entity.getRoles().stream()
                .map(x -> RoleSummaryResponse.builder()
                        .id(x.getId())
                        .roleInterfaceName(x.getRoleInterfaceName())
                        .roleInterfaceDescription(x.getRoleInterfaceDescription())
                        .build())
                .collect(Collectors.toList());
    }
}
