package com.aimtech.arrowcore.domain.business.dto.responses.management;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanySummaryResponse {

    private Long internalId;

    private UUID externalId;

    private String tradeName;

    private String corporateName;

    private String cnpj;

    private String stateRegistration;

    private String municipalRegistration;

    private String taxRegimeName;

    private String streetTypeAbbreviation;

    private String streetName;

    private String streetNumber;

    private String neighborhood;

    private String complement;

    private String zipCode;

    private String cityName;

    private String stateCode;

    private String CountryName;

    private Boolean isActive;

    private String parentCompanyCNPJ;
}
