package com.aimtech.arrowcore.infrastructure.controllers.management.addresses;

import com.aimtech.arrowcore.core.utils.ResourceUriHelper;
import com.aimtech.arrowcore.domain.business.dto.requests.management.CountryCreateRequest;
import com.aimtech.arrowcore.domain.business.dto.requests.management.CountryUpdateRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.management.CountryDetailResponse;
import com.aimtech.arrowcore.domain.business.usecases.management.country_module.*;
import com.aimtech.arrowcore.domain.enums.FilterStatusEnum;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/management/addresses/countries")
public class CountryController {
    private final FindAllCountryService findAllCountryService;
    private final FindCountryByInternalIdService findCountryByInternalIdService;
    private final FindCountryByIsoCodeService findCountryByIsoCodeService;
    private final CreateCountryService createCountryService;
    private final UpdateCountryService updateCountryService;
    private final ChangeCountryStatusService changeCountryStatusService;

    @GetMapping
    public ResponseEntity<Page<CountryDetailResponse>> findAll(
            Pageable pageable,
            @RequestParam(required = false, defaultValue = "ALL") FilterStatusEnum status
    ) {
        Page<CountryDetailResponse> result = this.findAllCountryService.execute(pageable, status);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{internalId}")
    public ResponseEntity<CountryDetailResponse> findById(@PathVariable @Valid Long internalId) {
        CountryDetailResponse result = this.findCountryByInternalIdService.execute(internalId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/find-by")
    public ResponseEntity<CountryDetailResponse> findByIsoCode(@RequestParam(name = "isoCode") @Valid String isoCode) {
        CountryDetailResponse result = this.findCountryByIsoCodeService.execute(isoCode);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody CountryCreateRequest request) {
        CountryDetailResponse result = this.createCountryService.execute(request);

        ResourceUriHelper.addUriInResponseHeader(result.getInternalId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{internalId}")
    public ResponseEntity<CountryDetailResponse> update(
            @Valid @RequestBody CountryUpdateRequest request,
            @PathVariable @Valid Long internalId
    ) {
        CountryDetailResponse result = this.updateCountryService.execute(request, internalId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PatchMapping("/{countryId}/change-status")
    public ResponseEntity<Void> changeStatus(@PathVariable Long countryId) {
        this.changeCountryStatusService.execute(countryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
