package com.aimtech.arrowcore.infrastructure.openapi.admin;

import com.aimtech.arrowcore.core.config.OpenAPIConfig;
import com.aimtech.arrowcore.domain.business.dto.requests.admin.ChangeUserPasswordRequest;
import com.aimtech.arrowcore.domain.business.dto.requests.admin.UserRegisterRequest;
import com.aimtech.arrowcore.domain.business.dto.requests.admin.UserUpdateRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.admin.UserDetailResponse;
import com.aimtech.arrowcore.domain.business.dto.responses.admin.UserSummaryResponse;
import com.aimtech.arrowcore.domain.enums.FilterStatusEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Admin | User")
@SecurityRequirement(name = OpenAPIConfig.SECURITY_SCHEME)
public interface UserControllerOpenApi {

    @GetMapping
    @Operation(
            summary = "Get a list of all users",
            description = "Retrieve a paginated list of users, optionally filtered by status."
    )
    @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    ResponseEntity<Page<UserSummaryResponse>> findAll(
            @Parameter(hidden = true) Pageable pageable,
            @RequestParam(required = false, defaultValue = "ALL") FilterStatusEnum status
    );

    @GetMapping("/{externalId}")
    @Operation(
            summary = "Get user details by ID",
            description = "Retrieve detailed information about a specific user by UUID."
    )
    @ApiResponse(responseCode = "200", description = "User details retrieved successfully")
    ResponseEntity<UserDetailResponse> findById(@PathVariable UUID externalId);

    @GetMapping("/me")
    @Operation(
            summary = "Get current logged-in user's details",
            description = "Retrieve detailed information about the currently logged-in user."
    )
    @ApiResponse(responseCode = "200", description = "Current user's details retrieved successfully")
    ResponseEntity<UserDetailResponse> getMe();

    @PostMapping
    @Operation(
            summary = "Register a new user",
            description = "Endpoint to register a new user."
    )
    @ApiResponse(responseCode = "201", description = "User registered successfully")
    ResponseEntity<Void> registerUser(@Valid @RequestBody UserRegisterRequest request);

    @PostMapping("/me/change-password")
    @Operation(
            summary = "Change current logged-in user's password",
            description = "Update the password of the currently logged-in user."
    )
    @ApiResponse(responseCode = "204", description = "Password changed successfully")
    ResponseEntity<Void> changeCurrentUserPassword(@Valid @RequestBody ChangeUserPasswordRequest request);

    @PutMapping("/{externalUserId}")
    @Operation(
            summary = "Update user details",
            description = "Update details of an existing user identified by their UUID."
    )
    @ApiResponse(responseCode = "200", description = "User details updated successfully")
    ResponseEntity<UserDetailResponse> update(
            @Valid @RequestBody UserUpdateRequest request,
            @PathVariable UUID externalUserId);

    @PatchMapping("/{externalUserId}/change-status")
    @Operation(
            summary = "Change user status",
            description = "Update the status of a user identified by their UUID."
    )
    @ApiResponse(responseCode = "204", description = "User status changed successfully")
    ResponseEntity<Void> changeStatus(@PathVariable UUID externalUserId);

    @PatchMapping("/{externalUserId}/unlock-account")
    @Operation(
            summary = "Unlock user account",
            description = "Unlock the account of a user identified by their UUID."
    )
    @ApiResponse(responseCode = "204", description = "User account unlocked successfully")
    ResponseEntity<Void> unlockAccount(@PathVariable UUID externalUserId);

    @PatchMapping("/{externalUserId}/reset-password")
    @Operation(
            summary = "Reset user password",
            description = "Reset the password of a user identified by their UUID."
    )
    @ApiResponse(responseCode = "204", description = "User password reset successfully")
    ResponseEntity<Void> resetPassword(@PathVariable UUID externalUserId);
}
