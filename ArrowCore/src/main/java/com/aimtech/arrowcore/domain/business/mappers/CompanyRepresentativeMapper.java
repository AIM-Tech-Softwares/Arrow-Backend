package com.aimtech.arrowcore.domain.business.mappers;

import com.aimtech.arrowcore.domain.business.dto.requests.CompanyRepresentativeCreateRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.CompanyRepresentativeDetailResponse;
import com.aimtech.arrowcore.domain.business.dto.responses.CompanyRepresentativeSummaryResponse;
import com.aimtech.arrowcore.domain.entities.CompanyRepresentative;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompanyRepresentativeMapper {

    @Mapping(target = "companies", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "internalId", ignore = true)
    CompanyRepresentative toEntity(CompanyRepresentativeCreateRequest dto);

    CompanyRepresentativeDetailResponse toDetailResponse(CompanyRepresentative entity);

    CompanyRepresentativeSummaryResponse toSummaryResponse(CompanyRepresentative entity);
}
