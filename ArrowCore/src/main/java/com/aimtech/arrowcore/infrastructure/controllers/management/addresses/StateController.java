package com.aimtech.arrowcore.infrastructure.controllers.management.addresses;

import com.aimtech.arrowcore.core.utils.ResourceUriHelper;
import com.aimtech.arrowcore.domain.business.dto.requests.management.StateCreateRequest;
import com.aimtech.arrowcore.domain.business.dto.requests.management.StateUpdateRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.management.StateDetailResponse;
import com.aimtech.arrowcore.domain.business.usecases.management.state_module.*;
import com.aimtech.arrowcore.domain.enums.FilterStatusEnum;
import com.aimtech.arrowcore.infrastructure.openapi.management.addresses.StateControllerOpenApi;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/management/addresses/states")
public class StateController implements StateControllerOpenApi {
    private final FindAllStatesServices findAllStatesServices;
    private final FindStateByInternalIdService findStateByInternalIdService;
    private final CreateStateService createStateService;
    private final UpdateStateService updateStateService;
    private final ChangeStateStatusService changeStateStatusService;


    @Override
    @GetMapping
    public ResponseEntity<Page<StateDetailResponse>> findAll(
            Pageable pageable,
            @RequestParam(required = false, defaultValue = "ALL") FilterStatusEnum status
    ) {
        Page<StateDetailResponse> result = this.findAllStatesServices.execute(pageable, status);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Override
    @GetMapping("/{internalId}")
    public ResponseEntity<StateDetailResponse> findStateByInternalId(@PathVariable Long internalId) {
        StateDetailResponse result = this.findStateByInternalIdService.execute(internalId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Override
    @PostMapping
    public ResponseEntity<Void> createState(@Valid @RequestBody StateCreateRequest request) {
        StateDetailResponse result = this.createStateService.execute(request);

        ResourceUriHelper.addUriInResponseHeader(result.getInternalId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @PutMapping("/{internalId}")
    public ResponseEntity<StateDetailResponse> updateState(
            @Valid @RequestBody StateUpdateRequest request,
            @PathVariable @Valid Long internalId
    ) {
        StateDetailResponse result = this.updateStateService.execute(request, internalId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Override
    @PatchMapping("/{stateId}/change-status")
    public ResponseEntity<Void> changeStatus(@PathVariable Long stateId) {
        this.changeStateStatusService.execute(stateId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
