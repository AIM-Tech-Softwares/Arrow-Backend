package com.aimtech.arrowcore.infrastructure.controllers;

import com.aimtech.arrowcore.core.utils.ResourceUriHelper;
import com.aimtech.arrowcore.domain.business.dto.requests.StreetTypeCreateRequest;
import com.aimtech.arrowcore.domain.business.dto.requests.StreetTypeUpdateRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.StreetTypeDetailResponse;
import com.aimtech.arrowcore.domain.business.dto.responses.StreetTypeSummaryResponse;
import com.aimtech.arrowcore.domain.business.usecases.streettype_module.*;
import com.aimtech.arrowcore.domain.enums.FilterStatusEnum;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/addresses/street-type")
public class StreetTypeController {
    private final FindAllStreetTypesService findAllStreetTypesService;
    private final FindStreetTypeByInternalIdService findStreetTypeByInternalIdService;
    private final CreateStreetTypeService createStreetTypeService;
    private final UpdateStreetTypeService updateStreetTypeService;
    private final ChangeStatusStreetTypeService changeStatusStreetTypeService;


    @GetMapping
    public ResponseEntity<Page<StreetTypeSummaryResponse>> findAll(
            Pageable pageable,
            @RequestParam(required = false, defaultValue = "ALL") FilterStatusEnum status
    ) {
        Page<StreetTypeSummaryResponse> result = this.findAllStreetTypesService.execute(pageable, status);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{streetTypeId}")
    public ResponseEntity<StreetTypeDetailResponse> findById(@Valid @PathVariable Long streetTypeId) {
        StreetTypeDetailResponse result = this.findStreetTypeByInternalIdService.execute(streetTypeId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody StreetTypeCreateRequest request) {
        StreetTypeDetailResponse result = this.createStreetTypeService.execute(request);

        ResourceUriHelper.addUriInResponseHeader(result.getInternalId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{streetTypeId}")
    public ResponseEntity<StreetTypeDetailResponse> update(
            @Valid @RequestBody StreetTypeUpdateRequest request,
            @Valid @PathVariable Long streetTypeId
    ) {
        StreetTypeDetailResponse result = this.updateStreetTypeService.execute(request, streetTypeId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PatchMapping("/{streetTypeId}/change-status")
    public ResponseEntity<Void> changeStatus(@PathVariable Long streetTypeId) {
        this.changeStatusStreetTypeService.execute(streetTypeId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
