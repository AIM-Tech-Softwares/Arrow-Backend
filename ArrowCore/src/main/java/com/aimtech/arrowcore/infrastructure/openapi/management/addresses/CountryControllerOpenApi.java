package com.aimtech.arrowcore.infrastructure.openapi.management.addresses;

import com.aimtech.arrowcore.domain.business.dto.requests.management.CountryCreateRequest;
import com.aimtech.arrowcore.domain.business.dto.requests.management.CountryUpdateRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.management.CountryDetailResponse;
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

@Tag(name = "Management | Country")
public interface CountryControllerOpenApi {

    @GetMapping
    @Operation(
            summary = "Get all countries",
            description = "Endpoint to get all countries."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful query",
                    content = @Content(
                            schema = @Schema(implementation = Page.class)
                    )),
    })
    ResponseEntity<Page<CountryDetailResponse>> findAll(
            Pageable pageable,
            @RequestParam FilterStatusEnum status
    );


    @GetMapping("/{internalId}")
    @Operation(
            summary = "Get country by ID",
            description = "Endpoint to get country details by ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful query",
                    content = @Content(
                            schema = @Schema(implementation = CountryDetailResponse.class)
                    )),
    })
    ResponseEntity<CountryDetailResponse> findById(
            @PathVariable Long internalId
    );


    @GetMapping("/find-by")
    @Operation(
            summary = "Get country by ISO Code",
            description = "Endpoint to get country details by ISO Code."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful query",
                    content = @Content(
                            schema = @Schema(implementation = CountryDetailResponse.class)
                    )),
    })
    ResponseEntity<CountryDetailResponse> findByIsoCode(
            @RequestParam String isoCode
    );


    @PostMapping
    @Operation(
            summary = "Create a new country",
            description = "Endpoint to create a new country."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Country created successfully"),
    })
    ResponseEntity<Void> create(
            @RequestBody(description = "New country data", required = true,
                    content = @Content(
                            schema = @Schema(implementation = CountryCreateRequest.class)
                    ))
            CountryCreateRequest request
    );


    @PutMapping("/{internalId}")
    @Operation(
            summary = "Update a country",
            description = "Endpoint to update a country."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Country updated successfully",
                    content = @Content(schema = @Schema(implementation = CountryDetailResponse.class))),
    })
    ResponseEntity<CountryDetailResponse> update(
            @RequestBody(description = "Country update data", required = true,
                    content = @Content(
                            schema = @Schema(implementation = CountryUpdateRequest.class)
                    ))
            CountryUpdateRequest request,
            @PathVariable Long internalId
    );


    @PatchMapping("/{countryId}/change-status")
    @Operation(
            summary = "Change country status",
            description = "Endpoint to change the status of a country."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Country status changed successfully"),
    })
    ResponseEntity<Void> changeStatus(
            @PathVariable Long countryId
    );
}
