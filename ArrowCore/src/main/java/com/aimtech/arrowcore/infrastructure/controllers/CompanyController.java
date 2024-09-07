package com.aimtech.arrowcore.infrastructure.controllers;

import com.aimtech.arrowcore.core.utils.ResourceUriHelper;
import com.aimtech.arrowcore.domain.business.dto.requests.CompanyRegisterRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.CompanyDetailResponse;
import com.aimtech.arrowcore.domain.business.usecases.company_module.CreateCompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
public class CompanyController {
    private final CreateCompanyService createCompanyService;


    @PostMapping
    private ResponseEntity<CompanyDetailResponse> registerCompany(
            @Valid @RequestBody CompanyRegisterRequest request
    ) {
        CompanyDetailResponse response = this.createCompanyService.execute(request);

        ResourceUriHelper.addUriInResponseHeader(response.getExternalId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
