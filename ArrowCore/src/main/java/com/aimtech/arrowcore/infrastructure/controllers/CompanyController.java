package com.aimtech.arrowcore.infrastructure.controllers;

import com.aimtech.arrowcore.core.annotation.CheckSecurity;
import com.aimtech.arrowcore.core.annotation.ValidCNPJ;
import com.aimtech.arrowcore.core.utils.ResourceUriHelper;
import com.aimtech.arrowcore.domain.business.dto.requests.CompanyCreateRequest;
import com.aimtech.arrowcore.domain.business.dto.requests.CompanyUpdateRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.CompanyDetailResponse;
import com.aimtech.arrowcore.domain.business.dto.responses.CompanySummaryResponse;
import com.aimtech.arrowcore.domain.business.usecases.company_module.*;
import com.aimtech.arrowcore.domain.enums.FilterStatusEnum;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
public class CompanyController {
    private final FindAllCompanyFilteringByStatusService findAllCompanyFilteringByStatusService;
    private final FindCompanyByExternalIdServices findCompanyByExternalIdServices;
    private final FindSubsidiaryCompaniesByParentCnpjService findSubsidiaryCompaniesByParentCnpjService;
    private final CreateCompanyService createCompanyService;
    private final UpdateCompanyService updateCompanyService;
    private final ChangeCompanyStatusService changeCompanyStatusService;

    @GetMapping
    @CheckSecurity.Company.CanRead
    public ResponseEntity<Page<CompanySummaryResponse>> findPageableCompanies(
            Pageable pageable,
            @RequestParam(required = false, defaultValue = "ALL")FilterStatusEnum status
    ) {
        Page<CompanySummaryResponse> result = this.findAllCompanyFilteringByStatusService.execute(pageable, status);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{externalId}")
    @CheckSecurity.Company.CanRead
    public ResponseEntity<CompanyDetailResponse> findCompanyByExternalId(@Valid @PathVariable UUID externalId) {
        CompanyDetailResponse result = this.findCompanyByExternalIdServices.execute(externalId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/subsidiary-companies/{parentCnpj}")
    @CheckSecurity.Company.CanRead
    public ResponseEntity<List<CompanySummaryResponse>> findSubsidiaryCompanies(@PathVariable @ValidCNPJ String parentCnpj) {
        List<CompanySummaryResponse> result = this.findSubsidiaryCompaniesByParentCnpjService.execute(parentCnpj);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping
    @CheckSecurity.Company.CanCreate
    public ResponseEntity<CompanyDetailResponse> createCompany(
            @Valid @RequestBody CompanyCreateRequest request
    ) {
        CompanyDetailResponse result = this.createCompanyService.execute(request);

        ResourceUriHelper.addUriInResponseHeader(result.getExternalId());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @PutMapping("/{externalId}")
    @CheckSecurity.Company.CanUpdate
    public ResponseEntity<CompanyDetailResponse> updateCompany(
            @Valid @RequestBody CompanyUpdateRequest request,
            @Valid @PathVariable UUID externalId
    ){
        CompanyDetailResponse result = this.updateCompanyService.execute(request, externalId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PatchMapping("/{externalId}/change-status")
    @CheckSecurity.Company.CanChangeRepresentatives
    public ResponseEntity<CompanyDetailResponse> changeCompanyStatus(
            @Valid @PathVariable UUID externalId
    ) {
        CompanyDetailResponse result = this.changeCompanyStatusService.execute(externalId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
