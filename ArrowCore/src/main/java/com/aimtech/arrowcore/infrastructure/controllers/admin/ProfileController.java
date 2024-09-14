package com.aimtech.arrowcore.infrastructure.controllers.admin;

import com.aimtech.arrowcore.core.annotation.CheckSecurity;
import com.aimtech.arrowcore.core.utils.ResourceUriHelper;
import com.aimtech.arrowcore.domain.business.dto.requests.admin.ProfileCreateRequest;
import com.aimtech.arrowcore.domain.business.dto.requests.admin.ProfileUpdateRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.ProfileSummaryResponse;
import com.aimtech.arrowcore.domain.business.dto.responses.admin.ProfileDetailResponse;
import com.aimtech.arrowcore.domain.business.usecases.admin.profile_module.*;
import com.aimtech.arrowcore.domain.enums.FilterStatusEnum;
import com.aimtech.arrowcore.infrastructure.openapi.admin.ProfileControllerOpenAPI;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/profiles")
public class ProfileController implements ProfileControllerOpenAPI {
    private final FindAllProfileService findAllProfileService;
    private final FindProfileByIdService findProfileByIdService;
    private final CreateProfileService createProfileService;
    private final UpdateProfileService updateProfileService;
    private final ChangeProfileStatusService changeProfileStatusService;

    @Override
    @GetMapping
    @CheckSecurity.Profile.CanRead
    public ResponseEntity<Page<ProfileSummaryResponse>> findAll(
            Pageable pageable,
            @RequestParam(required = false, defaultValue = "ALL") FilterStatusEnum status
    ) {
        Page<ProfileSummaryResponse> result = this.findAllProfileService.execute(pageable, status);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Override
    @GetMapping("/{profileId}")
    @CheckSecurity.Profile.CanRead
    public ResponseEntity<ProfileDetailResponse> findById(@Valid @PathVariable UUID profileId) {
        ProfileDetailResponse result = this.findProfileByIdService.execute(profileId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Override
    @PostMapping
    @CheckSecurity.Profile.CanCreate
    public ResponseEntity<Void> create(@Valid @RequestBody ProfileCreateRequest request) {
        ProfileDetailResponse result = this.createProfileService.execute(request);

        ResourceUriHelper.addUriInResponseHeader(result.getExternalId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @PutMapping("/{profileId}")
    @CheckSecurity.Profile.CanUpdate
    @CheckSecurity.Profile.CanChangeStatus
    public ResponseEntity<ProfileDetailResponse> update(
            @Valid @RequestBody ProfileUpdateRequest request,
            @Valid @PathVariable UUID profileId
    ) {
        ProfileDetailResponse result = this.updateProfileService.execute(request, profileId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Override
    @PatchMapping("/{profileId}/change-status")
    @CheckSecurity.Profile.CanChangeStatus
    public ResponseEntity<Void> changeStatus(@PathVariable UUID profileId) {
        this.changeProfileStatusService.execute(profileId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
