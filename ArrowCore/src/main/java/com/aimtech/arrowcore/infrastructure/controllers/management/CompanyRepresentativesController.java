package com.aimtech.arrowcore.infrastructure.controllers.management;

import com.aimtech.arrowcore.core.annotation.ValidCNPJ;
import com.aimtech.arrowcore.core.utils.ResourceUriHelper;
import com.aimtech.arrowcore.domain.business.dto.requests.management.CompanyRepresentativeCreateRequest;
import com.aimtech.arrowcore.domain.business.dto.requests.management.RepresentativeCompanyRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.management.CompanyRepresentativeDetailResponse;
import com.aimtech.arrowcore.domain.business.dto.responses.management.CompanyRepresentativeSummaryResponse;
import com.aimtech.arrowcore.domain.business.usecases.management.companyrepresentative_module.AssociateRepresentativeToCompanyService;
import com.aimtech.arrowcore.domain.business.usecases.management.companyrepresentative_module.CreateCompanyRepresentativeService;
import com.aimtech.arrowcore.domain.business.usecases.management.companyrepresentative_module.DissociateRepresentativeToCompanyService;
import com.aimtech.arrowcore.domain.business.usecases.management.companyrepresentative_module.FindRepresentationsByCompanyService;
import com.aimtech.arrowcore.infrastructure.openapi.management.CompanyRepresentativesControllerOpenApi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/management/company-representatives")
public class CompanyRepresentativesController implements CompanyRepresentativesControllerOpenApi {
    private final FindRepresentationsByCompanyService findRepresentationsByCompanyService;
    private final CreateCompanyRepresentativeService createCompanyRepresentativeService;
    private final AssociateRepresentativeToCompanyService associateRepresentativeToCompanyService;
    private final DissociateRepresentativeToCompanyService dissociateRepresentativeToCompanyService;

    @Override
    @GetMapping("/{cnpj}")
    public ResponseEntity<List<CompanyRepresentativeSummaryResponse>> findRepresentativesByCompanyCnpj(
            @ValidCNPJ @PathVariable String cnpj
    ) {
        List<CompanyRepresentativeSummaryResponse> result = this.findRepresentationsByCompanyService.execute(cnpj);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Override
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

    @Override
    @PatchMapping("/{externalCompanyId}/associate-representative")
    public ResponseEntity<Void> associateRepresentativeToCompany(
            @Valid @PathVariable UUID externalCompanyId,
            @Valid @RequestBody RepresentativeCompanyRequest request
    ) {
        associateRepresentativeToCompanyService.execute(request, externalCompanyId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    @PatchMapping("/{externalCompanyId}/dissociate-representative")
    public ResponseEntity<Void> dissociateRepresentativeToCompany(
            @Valid @PathVariable UUID externalCompanyId,
            @Valid @RequestBody RepresentativeCompanyRequest request
    ) {
        dissociateRepresentativeToCompanyService.execute(request, externalCompanyId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
