package com.aimtech.arrowcore.domain.business.mappers;

import com.aimtech.arrowcore.domain.business.dto.responses.BusinessGroupResponse;
import com.aimtech.arrowcore.domain.entities.BusinessGroup;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BusinessGroupMapper {

    BusinessGroupResponse toResponse(BusinessGroup dto);
}
