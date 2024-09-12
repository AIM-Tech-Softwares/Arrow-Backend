package com.aimtech.arrowcore.infrastructure.controllers.management.addresses;

import com.aimtech.arrowcore.core.annotation.CheckSecurity;
import com.aimtech.arrowcore.core.utils.ResourceUriHelper;
import com.aimtech.arrowcore.domain.business.dto.requests.management.CityCreateRequest;
import com.aimtech.arrowcore.domain.business.dto.requests.management.CityUpdateRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.management.CityDetailResponse;
import com.aimtech.arrowcore.domain.business.dto.responses.management.CitySummaryResponse;
import com.aimtech.arrowcore.domain.business.usecases.management.city_module.*;
import com.aimtech.arrowcore.domain.enums.FilterStatusEnum;
import com.aimtech.arrowcore.infrastructure.openapi.management.addresses.CityControllerOpenApi;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/management/addresses/cities")
public class CityController implements CityControllerOpenApi {
    private final FindAllCitiesService findAllCitiesService;
    private final FindCityByIdService findCityByIdService;
    private final CreateCityService createCityService;
    private final UpdateCityService updateCityService;
    private final ChangeCityStatusService changeCityStatusService;


    @Override
    @GetMapping
    @CheckSecurity.City.CanRead
    public ResponseEntity<Page<CitySummaryResponse>> findAll(
            Pageable pageable,
            @RequestParam(required = false, defaultValue = "ALL") FilterStatusEnum status
    ) {
        Page<CitySummaryResponse> result = this.findAllCitiesService.execute(pageable, status);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Override
    @GetMapping("/{cityId}")
    @CheckSecurity.City.CanRead
    public ResponseEntity<CityDetailResponse> findById(@Valid @PathVariable Long cityId) {
        CityDetailResponse result = this.findCityByIdService.execute(cityId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Override
    @PostMapping
    @CheckSecurity.City.CanCreate
    public ResponseEntity<Void> create(@Valid @RequestBody CityCreateRequest request) {
        CityDetailResponse result = this.createCityService.execute(request);

        ResourceUriHelper.addUriInResponseHeader(result.getInternalId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    @PutMapping("/{cityId}")
    @CheckSecurity.City.CanUpdate
    @CheckSecurity.City.CanChangeStatus
    public ResponseEntity<CityDetailResponse> update(
            @Valid @RequestBody CityUpdateRequest request,
            @Valid @PathVariable Long cityId
    ) {
        CityDetailResponse result = this.updateCityService.execute(request, cityId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @Override
    @PatchMapping("/{cityId}/change-status")
    @CheckSecurity.City.CanChangeStatus
    public ResponseEntity<Void> changeStatus(@PathVariable Long cityId) {
        this.changeCityStatusService.execute(cityId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
