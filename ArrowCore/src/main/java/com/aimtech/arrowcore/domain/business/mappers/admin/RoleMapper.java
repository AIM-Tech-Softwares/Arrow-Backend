package com.aimtech.arrowcore.domain.business.mappers.admin;

import com.aimtech.arrowcore.domain.business.dto.responses.admin.RoleSummaryResponse;
import com.aimtech.arrowcore.domain.entities.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleSummaryResponse toSummaryResponse(Role entity);
}
