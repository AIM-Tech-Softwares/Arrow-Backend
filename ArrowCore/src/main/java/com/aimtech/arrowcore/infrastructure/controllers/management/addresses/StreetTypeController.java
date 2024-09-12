package com.aimtech.arrowcore.infrastructure.controllers.management.addresses;

import com.aimtech.arrowcore.core.annotation.CheckSecurity;
import com.aimtech.arrowcore.core.utils.ResourceUriHelper;
import com.aimtech.arrowcore.domain.business.dto.requests.management.StreetTypeCreateRequest;
import com.aimtech.arrowcore.domain.business.dto.requests.management.StreetTypeUpdateRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.management.StreetTypeDetailResponse;
import com.aimtech.arrowcore.domain.business.dto.responses.management.StreetTypeSummaryResponse;
import com.aimtech.arrowcore.domain.business.usecases.management.streettype_module.*;
import com.aimtech.arrowcore.domain.enums.FilterStatusEnum;
import com.aimtech.arrowcore.infrastructure.openapi.management.addresses.StreetTypeControllerOpenApi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/management/addresses/street-type")
public class StreetTypeController implements StreetTypeControllerOpenApi {
    private final FindAllStreetTypesService findAllStreetTypesService;
    private final FindStreetTypeByInternalIdService findStreetTypeByInternalIdService;
    private final CreateStreetTypeService createStreetTypeService;
    private final UpdateStreetTypeService updateStreetTypeService;
    private final ChangeStatusStreetTypeService changeStatusStreetTypeService;


    @Override
    @GetMapping
    @CheckSecurity.StreetType.CanRead
    public ResponseEntity<Page<StreetTypeSummaryResponse>> findAll(
            Pageable pageable,
            @RequestParam(required = false, defaultValue = "ALL") FilterStatusEnum status
    ) {
        Page<StreetTypeSummaryResponse> result = this.findAllStreetTypesService.execute(pageable, status);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Override
    @GetMapping("/{streetTypeId}")
    @CheckSecurity.StreetType.CanRead
    public ResponseEntity<StreetTypeDetailResponse> findById(@Valid @PathVariable Long streetTypeId) {
        StreetTypeDetailResponse result = this.findStreetTypeByInternalIdService.execute(streetTypeId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Override
    @PostMapping
    @CheckSecurity.StreetType.CanCreate
    public ResponseEntity<Void> create(@Valid @RequestBody StreetTypeCreateRequest request) {
        StreetTypeDetailResponse result = this.createStreetTypeService.execute(request);

        ResourceUriHelper.addUriInResponseHeader(result.getInternalId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @PutMapping("/{streetTypeId}")
    @CheckSecurity.StreetType.CanUpdate
    public ResponseEntity<StreetTypeDetailResponse> update(
            @Valid @RequestBody StreetTypeUpdateRequest request,
            @Valid @PathVariable Long streetTypeId
    ) {
        StreetTypeDetailResponse result = this.updateStreetTypeService.execute(request, streetTypeId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Override
    @PatchMapping("/{streetTypeId}/change-status")
    @CheckSecurity.StreetType.CanChangeStatus
    public ResponseEntity<Void> changeStatus(@PathVariable Long streetTypeId) {
        this.changeStatusStreetTypeService.execute(streetTypeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
