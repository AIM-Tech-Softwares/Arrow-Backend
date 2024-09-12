package com.aimtech.arrowcore.domain.business.dto.responses.management;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "Internal identifier of the company", example = "12345")
    private Long internalId;

    @Schema(description = "External UUID of the company", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID externalId;

    @Schema(description = "Trade name of the company", example = "My Company")
    private String tradeName;

    @Schema(description = "Corporate name of the company", example = "My Company Inc.")
    private String corporateName;

    @Schema(description = "CNPJ of the company", example = "12345678000190")
    private String cnpj;

    @Schema(description = "State registration of the company", example = "123456789")
    private String stateRegistration;

    @Schema(description = "Municipal registration of the company", example = "987654321")
    private String municipalRegistration;

    @Schema(description = "Name of the company's tax regime", example = "Simples Nacional")
    private String taxRegimeName;

    @Schema(description = "Abbreviation of the street type", example = "St")
    private String streetTypeAbbreviation;

    @Schema(description = "Name of the street", example = "Main Street")
    private String streetName;

    @Schema(description = "Street number", example = "123")
    private String streetNumber;

    @Schema(description = "Neighborhood of the company", example = "Downtown")
    private String neighborhood;

    @Schema(description = "Complement to the address", example = "Suite 100")
    private String complement;

    @Schema(description = "ZIP code of the company", example = "12345678")
    private String zipCode;

    @Schema(description = "Name of the city", example = "New York")
    private String cityName;

    @Schema(description = "Code of the state", example = "NY")
    private String stateCode;

    @Schema(description = "Name of the country", example = "USA")
    private String countryName;

    @Schema(description = "Status indicating if the company is active", example = "true")
    private Boolean isActive;

    @Schema(description = "CNPJ of the parent company", example = "11222333000144")
    private String parentCompanyCNPJ;
}
