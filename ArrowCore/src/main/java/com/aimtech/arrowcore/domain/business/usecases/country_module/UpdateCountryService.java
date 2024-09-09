package com.aimtech.arrowcore.domain.business.usecases.country_module;

import com.aimtech.arrowcore.domain.business.dto.requests.CountryUpdateRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.CountryDetailResponse;
import com.aimtech.arrowcore.domain.business.mappers.CountryMapper;
import com.aimtech.arrowcore.domain.business.mappers.CountryUpdateMapper;
import com.aimtech.arrowcore.domain.entities.Country;
import com.aimtech.arrowcore.domain.repository.CountryRepository;
import com.aimtech.arrowcore.infrastructure.exceptions.ResourceNotFoundedException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateCountryService {
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;
    private final CountryUpdateMapper countryUpdateMapper;
    private final MessageSource messageSource;

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
