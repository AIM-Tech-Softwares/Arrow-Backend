package com.aimtech.arrowcore.domain.business.usecases.country_module;

import com.aimtech.arrowcore.domain.business.dto.requests.management.CountryUpdateRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.management.CountryDetailResponse;
import com.aimtech.arrowcore.domain.business.mappers.management.CountryMapper;
import com.aimtech.arrowcore.domain.business.mappers.management.CountryUpdateMapper;
import com.aimtech.arrowcore.domain.entities.Country;
import com.aimtech.arrowcore.domain.repository.CountryRepository;
import com.aimtech.arrowcore.infrastructure.exceptions.ResourceNotFoundedException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateCountryService {
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;
    private final CountryUpdateMapper countryUpdateMapper;
    private final MessageSource messageSource;

    @Transactional
    public CountryDetailResponse execute(CountryUpdateRequest request, Long internalId) {
        Country country = countryRepository.findById(internalId).orElseThrow(
                () -> new ResourceNotFoundedException(
                        messageSource.getMessage(
                                "arrowcore.exceptions.ResourceNotFoundedException",
                                new Object[]{"Country", "internalId: " + internalId},
                                LocaleContextHolder.getLocale()
                        )
                )
        );
        countryUpdateMapper.updateCountry(request, country);
        country = countryRepository.save(country);
        return countryMapper.toDetailResponse(country);
    }
}
