package com.aimtech.arrowcore.domain.business.mappers.management;

import com.aimtech.arrowcore.domain.business.dto.requests.management.CompanyCreateRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.management.CompanyDetailResponse;
import com.aimtech.arrowcore.domain.business.dto.responses.management.CompanySummaryResponse;
import com.aimtech.arrowcore.domain.entities.Company;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CompanyMapper {

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
    Company toEntity(CompanyCreateRequest dto);

    @Mapping(
            target = "parentCompanyCNPJ",
            expression = "java(entity.getParentCompany() != null ? entity.getParentCompany().getCnpj() : null)"
    )
    @Mapping(
            target = "countryName",
            expression = "java(entity.getCity().getState().getCountry().getCountryName())"
    )
    @Mapping(
            target = "streetTypeAbbreviation",
            expression = "java(entity.getStreetType().getStreetTypeAbbreviation())"
    )
    @Mapping(
            target = "stateCode",
            expression = "java(entity.getCity().getState().getStateCode())"
    )

    @Mapping(
            target = "cityName",
            expression = "java(entity.getCity().getCityName())"
    )
    CompanyDetailResponse toDetailResponse(Company entity);


    @Mapping(
            target = "parentCompanyCNPJ",
            expression = "java(entity.getParentCompany() != null ? entity.getParentCompany().getCnpj() : null)"
    )
    @Mapping(
            target = "countryName",
            expression = "java(entity.getCity().getState().getCountry().getCountryName())"
    )
    @Mapping(
            target = "streetTypeAbbreviation",
            expression = "java(entity.getStreetType().getStreetTypeAbbreviation())"
    )
    @Mapping(
            target = "stateCode",
            expression = "java(entity.getCity().getState().getStateCode())"
    )
    @Mapping(
            target = "taxRegimeName",
            expression = "java(entity.getTaxRegime().getTaxRegimeName())"
    )
    @Mapping(
            target = "cityName",
            expression = "java(entity.getCity().getCityName())"
    )
    CompanySummaryResponse toSummaryResponse(Company entity);
}
