package com.aimtech.arrowcore.infrastructure.controllers;

import com.aimtech.arrowcore.core.utils.ResourceUriHelper;
import com.aimtech.arrowcore.domain.business.dto.requests.CompanyRepresentativeCreateRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.CompanyRepresentativeDetailResponse;
import com.aimtech.arrowcore.domain.business.dto.responses.CompanyRepresentativeSummaryResponse;
import com.aimtech.arrowcore.domain.business.usecases.companyrepresentative_module.CreateCompanyRepresentativeService;
import com.aimtech.arrowcore.domain.business.usecases.companyrepresentative_module.FindRepresentationsByCompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/company-representatives")
public class CompanyRepresentativesController {
    private final FindRepresentationsByCompanyService findRepresentationsByCompanyService;
    private final CreateCompanyRepresentativeService createCompanyRepresentativeService;

    @GetMapping("/{cnpj}")
    public ResponseEntity<List<CompanyRepresentativeSummaryResponse>> findRepresentativesByCompanyCnpj(
            @PathVariable String cnpj
    ) {
        List<CompanyRepresentativeSummaryResponse> result = this.findRepresentationsByCompanyService.execute(cnpj);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/{externalCompanyId}")
    public ResponseEntity<Void> createCompanyRepresentative(
            @Valid @PathVariable UUID externalCompanyId,
            @Valid @RequestBody CompanyRepresentativeCreateRequest request
            ) {
        CompanyRepresentativeDetailResponse result = createCompanyRepresentativeService
                .execute(request, externalCompanyId);

        ResourceUriHelper.addUriInResponseHeader(result.getInternalId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
