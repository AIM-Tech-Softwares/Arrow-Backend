package com.aimtech.arrowcore.infrastructure.openapi.management;

import com.aimtech.arrowcore.core.annotation.ValidCNPJ;
import com.aimtech.arrowcore.domain.business.dto.requests.management.CompanyRepresentativeCreateRequest;
import com.aimtech.arrowcore.domain.business.dto.requests.management.RepresentativeCompanyRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.management.CompanyRepresentativeSummaryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.UUID;

@Tag(name = "Management | CompanyRepresentatives")
public interface CompanyRepresentativesControllerOpenApi {

    @GetMapping("/{cnpj}")
    @Operation(
            summary = "Get representatives by company CNPJ",
            description = "Endpoint to get representatives by company CNPJ."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful query",
                    content = @Content(schema = @Schema(implementation = List.class))),
            @ApiResponse(responseCode = "404", description = "Company not found")
    })
    ResponseEntity<List<CompanyRepresentativeSummaryResponse>> findRepresentativesByCompanyCnpj(
            @ValidCNPJ @PathVariable String cnpj
    );

    @PostMapping("/{externalCompanyId}")
    @Operation(
            summary = "Create a new company representative",
            description = "Endpoint to create a new company representative."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Company representative created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    ResponseEntity<Void> createCompanyRepresentative(
            @Valid @PathVariable UUID externalCompanyId,
            CompanyRepresentativeCreateRequest request
    );

    @PatchMapping("/{externalCompanyId}/associate-representative")
    @Operation(
            summary = "Associate representative to company",
            description = "Endpoint to associate a representative to a company."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully associated"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    ResponseEntity<Void> associateRepresentativeToCompany(
            @Valid @PathVariable UUID externalCompanyId,
            RepresentativeCompanyRequest request
    );

    @PatchMapping("/{externalCompanyId}/dissociate-representative")
    @Operation(
            summary = "Dissociate representative from company",
            description = "Endpoint to dissociate a representative from a company."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully dissociated"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    ResponseEntity<Void> dissociateRepresentativeToCompany(
            @Valid @PathVariable UUID externalCompanyId,
            RepresentativeCompanyRequest request
    );

}
