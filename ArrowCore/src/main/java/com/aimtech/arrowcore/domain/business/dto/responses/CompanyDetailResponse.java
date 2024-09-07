package com.aimtech.arrowcore.domain.business.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDetailResponse {

    private Long internalId;

    private UUID externalId;

    private String tradeName;

    private String corporateName;

    private String cnpj;

    private String stateRegistration;

    private String municipalRegistration;

    private Date foundationDate;

    private TaxRegimeDetailResponse taxRegime;

    private String email;

    private String phone;

    private String streetTypeAbbreviation;

    private String streetName;

    private String streetNumber;

    private String neighborhood;

    private String complement;

    private String zipCode;

    private String cityName;

    private String stateCode;

    private String CountryName;

    private String logoUrl;

    private Boolean isActive;

    private List<CompanyRepresentativeDetailResponse> representatives;

    private String parentCompanyCNPJ;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;
}
