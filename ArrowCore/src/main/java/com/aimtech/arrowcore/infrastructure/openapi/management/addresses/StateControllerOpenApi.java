package com.aimtech.arrowcore.infrastructure.openapi.management.addresses;

import com.aimtech.arrowcore.core.config.OpenAPIConfig;
import com.aimtech.arrowcore.domain.business.dto.requests.management.StateCreateRequest;
import com.aimtech.arrowcore.domain.business.dto.requests.management.StateUpdateRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.management.StateDetailResponse;
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

@Tag(name = "Management | State")
@SecurityRequirement(name = OpenAPIConfig.SECURITY_SCHEME)
public interface StateControllerOpenApi {


    @GetMapping
    @Operation(
            summary = "Get all states",
            description = "Endpoint to get all states."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful query",
                    content = @Content(
                            schema = @Schema(implementation = Page.class)
                    )),
    })
    ResponseEntity<Page<StateDetailResponse>> findAll(
            @Parameter(hidden = true) Pageable pageable,
            @RequestParam FilterStatusEnum status
    );


    @GetMapping("/{internalId}")
    @Operation(
            summary = "Get state by ID",
            description = "Endpoint to get state details by ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful query",
                    content = @Content(
                            schema = @Schema(implementation = StateDetailResponse.class)
                    )),
    })
    ResponseEntity<StateDetailResponse> findStateByInternalId(@PathVariable Long internalId);


    @PostMapping
    @Operation(
            summary = "Create a new state",
            description = "Endpoint to create a new state."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "State created successfully"),
    })
    ResponseEntity<Void> createState(
            @RequestBody(description = "New state data", required = true,
                    content = @Content(
                            schema = @Schema(implementation = StateCreateRequest.class)
                    ))
            StateCreateRequest request
    );


    @PutMapping("/{internalId}")
    @Operation(
            summary = "Update a state",
            description = "Endpoint to update a state."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "State updated successfully",
                    content = @Content(
                            schema = @Schema(implementation = StateDetailResponse.class)
                    )),
    })
    ResponseEntity<StateDetailResponse> updateState(
            @RequestBody(description = "State update data", required = true,
                    content = @Content(
                            schema = @Schema(implementation = StateUpdateRequest.class)
                    ))
            StateUpdateRequest request,
            @PathVariable Long internalId
    );


    @PatchMapping("/{stateId}/change-status")
    @Operation(
            summary = "Change state status",
            description = "Endpoint to change the status of a state."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "State status changed successfully"),
    })
    ResponseEntity<Void> changeStatus(
            @PathVariable Long stateId
    );
}
