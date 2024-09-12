package com.aimtech.arrowcore.domain.business.dto.responses.management;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDetailResponse {

    @Schema(description = "Internal identifier of the company", example = "12345")
    private Long internalId;

    @Schema(description = "External UUID of the company", example = "550e8400-e29b-41d4-a716-446655440000")
    private UUID externalId;

    @Schema(description = "Trade name of the company", example = "My Company")
    private String tradeName;

    @Schema(description = "Corporate name of the company", example = "My Company Inc.")
    private String corporateName;

    @Schema(description = "CNPJ of the company", example = "12.345.678/0001-90")
    private String cnpj;

    @Schema(description = "State registration of the company", example = "123456789")
    private String stateRegistration;

    @Schema(description = "Municipal registration of the company", example = "987654321")
    private String municipalRegistration;

    @Schema(description = "Foundation date of the company", example = "2000-01-01")
    private Date foundationDate;

    @Schema(description = "Tax regime of the company")
    private TaxRegimeDetailResponse taxRegime;

    @Schema(description = "Email of the company", example = "contact@company.com")
    private String email;

    @Schema(description = "Phone number of the company", example = "+1 234 567 890")
    private String phone;

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

    @Schema(description = "ZIP code of the company", example = "12345-678")
    private String zipCode;

    @Schema(description = "Name of the city", example = "New York")
    private String cityName;

    @Schema(description = "Code of the state", example = "NY")
    private String stateCode;

    @Schema(description = "Name of the country", example = "USA")
    private String countryName;

    @Schema(description = "URL of the company logo", example = "https://company.com/logo.png")
    private String logoUrl;

    @Schema(description = "Status indicating if the company is active", example = "true")
    private Boolean isActive;

    @Schema(description = "List of company representatives")
    private List<CompanyRepresentativeDetailResponse> representatives;

    @Schema(description = "CNPJ of the parent company", example = "11.222.333/0001-44")
    private String parentCompanyCNPJ;

    @Schema(description = "Timestamp when the company details were created", example = "2023-10-01T12:34:56Z")
    private OffsetDateTime createdAt;

    @Schema(description = "Timestamp when the company details were last updated", example = "2023-10-10T15:20:35Z")
    private OffsetDateTime updatedAt;
}
