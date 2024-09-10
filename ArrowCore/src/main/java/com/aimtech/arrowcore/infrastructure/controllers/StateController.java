package com.aimtech.arrowcore.infrastructure.controllers;

import com.aimtech.arrowcore.core.utils.ResourceUriHelper;
import com.aimtech.arrowcore.domain.business.dto.requests.StateCreateRequest;
import com.aimtech.arrowcore.domain.business.dto.requests.StateUpdateRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.StateDetailResponse;
import com.aimtech.arrowcore.domain.business.usecases.state_module.CreateStateService;
import com.aimtech.arrowcore.domain.business.usecases.state_module.FindAllStatesServices;
import com.aimtech.arrowcore.domain.business.usecases.state_module.FindStateByInternalIdService;
import com.aimtech.arrowcore.domain.business.usecases.state_module.UpdateStateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/addresses/states")
public class StateController {
    private final FindAllStatesServices findAllStatesServices;
    private final FindStateByInternalIdService findStateByInternalIdService;
    private final CreateStateService createStateService;
    private final UpdateStateService updateStateService;


    @GetMapping
    public ResponseEntity<Page<StateDetailResponse>> findAll(Pageable pageable) {
        Page<StateDetailResponse> result = this.findAllStatesServices.execute(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{internalId}")
    public ResponseEntity<StateDetailResponse> findStateByInternalId(@PathVariable Long internalId) {
        StateDetailResponse result = this.findStateByInternalIdService.execute(internalId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping
    public ResponseEntity<Void> createState(@Valid @RequestBody StateCreateRequest request) {
        StateDetailResponse result = this.createStateService.execute(request);

        ResourceUriHelper.addUriInResponseHeader(result.getInternalId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{internalId}")
    public ResponseEntity<StateDetailResponse> updateState(
            @Valid @RequestBody StateUpdateRequest request,
            @PathVariable @Valid Long internalId
    ) {
        StateDetailResponse result = this.updateStateService.execute(request, internalId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
