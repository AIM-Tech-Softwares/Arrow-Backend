package com.aimtech.arrowcore.infrastructure.controllers;

import com.aimtech.arrowcore.core.annotation.ValidCNPJ;
import com.aimtech.arrowcore.core.utils.ResourceUriHelper;
import com.aimtech.arrowcore.domain.business.dto.requests.CompanyRegisterRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.CompanyDetailResponse;
import com.aimtech.arrowcore.domain.business.dto.responses.CompanySummaryResponse;
import com.aimtech.arrowcore.domain.business.usecases.company_module.CreateCompanyService;
import com.aimtech.arrowcore.domain.business.usecases.company_module.FindCompanyByExternalIdServices;
import com.aimtech.arrowcore.domain.business.usecases.company_module.FindSubsidiaryCompaniesByParentCnpjService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
public class CompanyController {
    private final FindCompanyByExternalIdServices findCompanyByExternalIdServices;
    private final FindSubsidiaryCompaniesByParentCnpjService findSubsidiaryCompaniesByParentCnpjService;
    private final CreateCompanyService createCompanyService;


    @GetMapping("/{externalId}")
    private ResponseEntity<CompanyDetailResponse> findCompanyByExternalId(@PathVariable UUID externalId) {
        CompanyDetailResponse result = this.findCompanyByExternalIdServices.execute(externalId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/subsidiary-companies/{parentCnpj}")
    private ResponseEntity<List<CompanySummaryResponse>> findSubsidiaryCompanies(@PathVariable @ValidCNPJ String parentCnpj) {
        List<CompanySummaryResponse> result = this.findSubsidiaryCompaniesByParentCnpjService.execute(parentCnpj);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping
    private ResponseEntity<CompanyDetailResponse> registerCompany(
            @Valid @RequestBody CompanyRegisterRequest request
    ) {
        CompanyDetailResponse result = this.createCompanyService.execute(request);

        ResourceUriHelper.addUriInResponseHeader(result.getExternalId());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }
}
