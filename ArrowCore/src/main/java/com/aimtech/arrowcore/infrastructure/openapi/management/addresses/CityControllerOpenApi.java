package com.aimtech.arrowcore.infrastructure.openapi.management.addresses;

import com.aimtech.arrowcore.domain.business.dto.requests.management.CityCreateRequest;
import com.aimtech.arrowcore.domain.business.dto.requests.management.CityUpdateRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.management.CityDetailResponse;
import com.aimtech.arrowcore.domain.business.dto.responses.management.CitySummaryResponse;
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

@Tag(name = "Management | City")
public interface CityControllerOpenApi {

    @GetMapping
    @Operation(
            summary = "Get all cities",
            description = "Endpoint to get all cities."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful query",
                    content = @Content(
                            schema = @Schema(implementation = Page.class)
                    )),
    })
    ResponseEntity<Page<CitySummaryResponse>> findAll(
            Pageable pageable,
            @RequestParam FilterStatusEnum status
    );


    @GetMapping("/{cityId}")
    @Operation(
            summary = "Get city by ID",
            description = "Endpoint to get city details by ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful query",
                    content = @Content(
                            schema = @Schema(implementation = CityDetailResponse.class)
                    )),
    })
    ResponseEntity<CityDetailResponse> findById(
            @PathVariable Long cityId
    );


    @PostMapping
    @Operation(
            summary = "Create a new city",
            description = "Endpoint to create a new city."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "City created successfully")
    })
    ResponseEntity<Void> create(
            @RequestBody(description = "New city data", required = true,
                    content = @Content(
                            schema = @Schema(implementation = CityCreateRequest.class)
                    ))
            CityCreateRequest request
    );


    @PutMapping("/{cityId}")
    @Operation(
            summary = "Update a city",
            description = "Endpoint to update a city."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "City updated successfully",
                    content = @Content(
                            schema = @Schema(implementation = CityDetailResponse.class)
                    ))
    })
    ResponseEntity<CityDetailResponse> update(
            @RequestBody(description = "City update data", required = true,
                    content = @Content(
                            schema = @Schema(implementation = CityUpdateRequest.class)
                    ))
            CityUpdateRequest request,
            @PathVariable Long cityId
    );


    @PatchMapping("/{cityId}/change-status")
    @Operation(
            summary = "Change city status",
            description = "Endpoint to change the status of a city."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "City status changed successfully"),
    })
    ResponseEntity<Void> changeStatus(
            @PathVariable Long cityId
    );
}