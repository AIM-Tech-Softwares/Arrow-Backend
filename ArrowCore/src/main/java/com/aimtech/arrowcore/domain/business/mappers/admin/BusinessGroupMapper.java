package com.aimtech.arrowcore.domain.business.mappers.admin;

import com.aimtech.arrowcore.domain.business.dto.responses.admin.BusinessGroupResponse;
import com.aimtech.arrowcore.domain.entities.BusinessGroup;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BusinessGroupMapper {

    BusinessGroupResponse toResponse(BusinessGroup dto);
}
