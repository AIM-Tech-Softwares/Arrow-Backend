package com.aimtech.arrowcore.infrastructure.openapi.admin;

import com.aimtech.arrowcore.core.config.OpenAPIConfig;
import com.aimtech.arrowcore.domain.business.dto.requests.admin.ProfileCreateRequest;
import com.aimtech.arrowcore.domain.business.dto.requests.admin.ProfileUpdateRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.ProfileSummaryResponse;
import com.aimtech.arrowcore.domain.business.dto.responses.admin.ProfileDetailResponse;
import com.aimtech.arrowcore.domain.enums.FilterStatusEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Admin | Profile")
@SecurityRequirement(name = OpenAPIConfig.SECURITY_SCHEME)
public interface ProfileControllerOpenAPI {

    @GetMapping
    @Operation(
            summary = "Get all profiles",
            description = "Endpoint to get all profiles."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful query",
                    content = @Content(
                            schema = @Schema(implementation = Page.class)
                    )),
    })
    ResponseEntity<Page<ProfileSummaryResponse>> findAll(
            @Parameter(hidden = true) Pageable pageable,
            @RequestParam(required = false, defaultValue = "ALL") FilterStatusEnum status
    );

    @GetMapping("/{profileId}")
    @Operation(
            summary = "Get profile by ID",
            description = "Endpoint to get profile details by ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful query",
                    content = @Content(
                            schema = @Schema(implementation = ProfileDetailResponse.class)
                    )),
    })
    ResponseEntity<ProfileDetailResponse> findById(
            @PathVariable UUID profileId
    );

    @PostMapping
    @Operation(
            summary = "Create a new profile",
            description = "Endpoint to create a new profile."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Profile created successfully")
    })
    ResponseEntity<Void> create(
            @RequestBody(description = "New profile data", required = true,
                    content = @Content(
                            schema = @Schema(implementation = ProfileCreateRequest.class)
                    ))
            ProfileCreateRequest request
    );

    @PutMapping("/{profileId}")
    @Operation(
            summary = "Update a profile",
            description = "Endpoint to update a profile."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile updated successfully",
                    content = @Content(
                            schema = @Schema(implementation = ProfileDetailResponse.class)
                    ))
    })
    ResponseEntity<ProfileDetailResponse> update(
            @RequestBody(description = "Profile update data", required = true,
                    content = @Content(
                            schema = @Schema(implementation = ProfileUpdateRequest.class)
                    ))
            ProfileUpdateRequest request,
            @PathVariable UUID profileId
    );

    @PatchMapping("/{profileId}/change-status")
    @Operation(
            summary = "Change profile status",
            description = "Endpoint to change the status of a profile."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Profile status changed successfully"),
    })
    ResponseEntity<Void> changeStatus(
            @PathVariable UUID profileId
    );

}
