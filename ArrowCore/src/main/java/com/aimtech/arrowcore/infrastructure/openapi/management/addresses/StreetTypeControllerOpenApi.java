package com.aimtech.arrowcore.infrastructure.openapi.management.addresses;

import com.aimtech.arrowcore.domain.business.dto.requests.management.StreetTypeCreateRequest;
import com.aimtech.arrowcore.domain.business.dto.requests.management.StreetTypeUpdateRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.management.StreetTypeDetailResponse;
import com.aimtech.arrowcore.domain.business.dto.responses.management.StreetTypeSummaryResponse;
import com.aimtech.arrowcore.domain.enums.FilterStatusEnum;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Management | StreetType")
public interface StreetTypeControllerOpenApi {


    @GetMapping
    @Operation(
            summary = "Get all street types",
            description = "Endpoint to get all street types."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful query",
                    content = @Content(
                            schema = @Schema(implementation = Page.class)
                    )),
    })
    ResponseEntity<Page<StreetTypeSummaryResponse>> findAll(
            Pageable pageable,
            @RequestParam FilterStatusEnum status
    );


    @GetMapping("/{streetTypeId}")
    @Operation(summary = "Get street type by ID", description = "Endpoint to get street type details by ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful query",
                    content = @Content(
                            schema = @Schema(implementation = StreetTypeDetailResponse.class)
                    )),
    })
    ResponseEntity<StreetTypeDetailResponse> findById(
            @PathVariable Long streetTypeId
    );


    @PostMapping
    @Operation(
            summary = "Create a new street type",
            description = "Endpoint to create a new street type."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Street type created successfully"),
    })
    ResponseEntity<Void> create(
            @RequestBody(description = "New street type data", required = true,
                    content = @Content(
                            schema = @Schema(implementation = StreetTypeCreateRequest.class)
                    ))
            StreetTypeCreateRequest request
    );


    @PutMapping("/{streetTypeId}")
    @Operation(
            summary = "Update a street type",
            description = "Endpoint to update a street type."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Street type updated successfully",
                    content = @Content(
                            schema = @Schema(implementation = StreetTypeDetailResponse.class)
                    )),
    })
    ResponseEntity<StreetTypeDetailResponse> update(
            @RequestBody(description = "Street type update data", required = true,
                    content = @Content(
                            schema = @Schema(implementation = StreetTypeUpdateRequest.class)
                    ))
            StreetTypeUpdateRequest request,
            @PathVariable Long streetTypeId
    );


    @PatchMapping("/{streetTypeId}/change-status")
    @Operation(
            summary = "Change street type status",
            description = "Endpoint to change the status of a street type."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Street type status changed successfully"),
    })
    ResponseEntity<Void> changeStatus(
            @PathVariable Long streetTypeId
    );
}
