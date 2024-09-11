package com.aimtech.arrowcore.infrastructure.openapi.management;

import com.aimtech.arrowcore.domain.business.dto.requests.management.CompanyCreateRequest;
import com.aimtech.arrowcore.domain.business.dto.requests.management.CompanyUpdateRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.management.CompanyDetailResponse;
import com.aimtech.arrowcore.domain.business.dto.responses.management.CompanySummaryResponse;
import com.aimtech.arrowcore.domain.enums.FilterStatusEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Management | Company")
public interface CompanyControllerOpenApi {


    @GetMapping
    @Operation(summary = "Get all companies", description = "Endpoint to get all companies.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful query",
                    content = @Content(
                            schema = @Schema(implementation = Page.class)
                    )),
    })
    ResponseEntity<Page<CompanySummaryResponse>> findPageableCompanies(
            Pageable pageable,
            FilterStatusEnum status
    );


    @GetMapping("/{externalId}")
    @Operation(
            summary = "Get company by external ID",
            description = "Endpoint to get company details by external ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful query",
                    content = @Content(
                            schema = @Schema(implementation = CompanyDetailResponse.class)
                    )),
    })
    ResponseEntity<CompanyDetailResponse> findCompanyByExternalId(
            @Valid @PathVariable UUID externalId
    );


    @GetMapping("/subsidiary-companies/{parentCnpj}")
    @Operation(
            summary = "Get subsidiary companies by parent CNPJ",
            description = "Endpoint to get subsidiary companies by parent CNPJ."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful query",
                    content = @Content(
                            schema = @Schema(implementation = List.class)
                    )),
    })
    ResponseEntity<List<CompanySummaryResponse>> findSubsidiaryCompanies(
            @PathVariable String parentCnpj
    );


    @PostMapping
    @Operation(
            summary = "Create a new company",
            description = "Endpoint to create a new company."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Company created successfully"),
    })
    ResponseEntity<CompanyDetailResponse> createCompany(
            CompanyCreateRequest request
    );


    @PutMapping("/{externalId}")
    @Operation(
            summary = "Update a company",
            description = "Endpoint to update a company."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated",
                    content = @Content(
                            schema = @Schema(implementation = CompanyDetailResponse.class)
                    )),
    })
    ResponseEntity<CompanyDetailResponse> updateCompany(
            @Valid @RequestBody CompanyUpdateRequest request,
            @Valid @PathVariable UUID externalId
    );


    @PatchMapping("/{externalId}/change-status")
    @Operation(
            summary = "Change company status",
            description = "Endpoint to change the status of a company."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully changed status",
                    content = @Content(
                            schema = @Schema(implementation = CompanyDetailResponse.class)
                    )),
    })
    ResponseEntity<CompanyDetailResponse> changeCompanyStatus(
            @Valid @PathVariable UUID externalId
    );
}
