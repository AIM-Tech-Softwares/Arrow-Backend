package com.aimtech.arrowcore.domain.business.mappers;

import com.aimtech.arrowcore.domain.business.dto.requests.CompanyUpdateRequest;
import com.aimtech.arrowcore.domain.entities.Company;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CompanyUpdateMapper {

    @Mapping(target = "cnpj", ignore = true)
    @Mapping(target = "city", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "taxRegime", ignore = true)
    @Mapping(target = "streetType", ignore = true)
    @Mapping(target = "externalId", ignore = true)
    @Mapping(target = "internalId", ignore = true)
    @Mapping(target = "parentCompany", ignore = true)
    @Mapping(target = "businessGroup", ignore = true)
    @Mapping(target = "representatives", ignore = true)
    void updateCompany(CompanyUpdateRequest dto, @MappingTarget Company entity);
}
