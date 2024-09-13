package com.aimtech.arrowcore.infrastructure.controllers.admin;

import com.aimtech.arrowcore.core.annotation.CheckSecurity;
import com.aimtech.arrowcore.core.utils.ResourceUriHelper;
import com.aimtech.arrowcore.domain.business.dto.requests.admin.ChangeUserPasswordRequest;
import com.aimtech.arrowcore.domain.business.dto.requests.admin.UserRegisterRequest;
import com.aimtech.arrowcore.domain.business.dto.requests.admin.UserUpdateRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.admin.UserDetailResponse;
import com.aimtech.arrowcore.domain.business.dto.responses.admin.UserRegisterResponse;
import com.aimtech.arrowcore.domain.business.dto.responses.admin.UserSummaryResponse;
import com.aimtech.arrowcore.domain.business.usecases.admin.user_module.*;
import com.aimtech.arrowcore.domain.enums.FilterStatusEnum;
import com.aimtech.arrowcore.infrastructure.openapi.admin.UserControllerOpenApi;
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
@RequestMapping("/admin/users")
public class UserController implements UserControllerOpenApi {
    private final FindAllUsersServices findAllUsersServices;
    private final FindUserByExternalIdService findUserByExternalIdService;
    private final GetCurrentUserService getCurrentUserService;
    private final CreateUserService createUserService;
    private final UpdateUserService updateUserService;
    private final ChangeUserStatusService changeUserStatusService;
    private final ChangeUserPasswordService changeUserPasswordService;
    private final ResetByExternalIdUserPasswordService resetByExternalIdUserPasswordService;
    private final UnlockUserAccountService unlockUserAccountService;

    @GetMapping
    @CheckSecurity.User.CanReadExt
    public ResponseEntity<Page<UserSummaryResponse>> findAll(
            Pageable pageable,
            @RequestParam(required = false, defaultValue = "ALL") FilterStatusEnum status
    ) {
        Page<UserSummaryResponse> result = this.findAllUsersServices.execute(pageable, status);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{externalId}")
    @CheckSecurity.User.CanReadExt
    public ResponseEntity<UserDetailResponse> findById(@PathVariable UUID externalId) {
        UserDetailResponse result = this.findUserByExternalIdService.execute(externalId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/me")
    @CheckSecurity.User.CanRead
    public ResponseEntity<UserDetailResponse> getMe() {
        UserDetailResponse result = this.getCurrentUserService.execute();
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Override
    @PostMapping
    @CheckSecurity.User.CanCreate
    public ResponseEntity<Void> registerUser(@Valid @RequestBody UserRegisterRequest request) {
        UserRegisterResponse response = this.createUserService.execute(request);

        ResourceUriHelper.addUriInResponseHeader(response.getExternalId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/me/change-password")
    @CheckSecurity.User.CanChangeMePassword
    public ResponseEntity<Void> changeCurrentUserPassword(
            @Valid @RequestBody ChangeUserPasswordRequest request
    ) {
        this.changeUserPasswordService.execute(request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{externalUserId}")
    @CheckSecurity.User.CanUpdate
    public ResponseEntity<UserDetailResponse> update(
            @Valid @RequestBody UserUpdateRequest request,
            @Valid @PathVariable UUID externalUserId
    ) {
        UserDetailResponse result = this.updateUserService.execute(request, externalUserId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PatchMapping("/{externalUserId}/change-status")
    @CheckSecurity.User.CanChangeStatus
    public ResponseEntity<Void> changeStatus(@Valid @PathVariable UUID externalUserId) {
        this.changeUserStatusService.execute(externalUserId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{externalUserId}/unlock-account")
    @CheckSecurity.User.CanUnlockExtAccount
    public ResponseEntity<Void> unlockAccount(@Valid @PathVariable UUID externalUserId) {
        this.unlockUserAccountService.execute(externalUserId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{externalUserId}/reset-password")
    @CheckSecurity.User.CanChangeExtPassword
    public ResponseEntity<Void> resetPassword(@Valid @PathVariable UUID externalUserId) {
        this.resetByExternalIdUserPasswordService.execute(externalUserId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
