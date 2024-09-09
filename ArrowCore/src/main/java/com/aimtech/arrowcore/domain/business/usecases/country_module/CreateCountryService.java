package com.aimtech.arrowcore.domain.business.usecases.country_module;

import com.aimtech.arrowcore.domain.business.dto.requests.CountryCreateRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.CountryDetailResponse;
import com.aimtech.arrowcore.domain.business.mappers.CountryMapper;
import com.aimtech.arrowcore.domain.entities.Country;
import com.aimtech.arrowcore.domain.repository.CountryRepository;
import com.aimtech.arrowcore.infrastructure.exceptions.DuplicateResourceException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCountryService {
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;
    private final MessageSource messageSource;

    public CountryDetailResponse execute(CountryCreateRequest request) {
        if (countryRepository.existsByIsoCode(request.getIsoCode())) {
            throw new DuplicateResourceException(
                    messageSource.getMessage(
                            "arrowcore.exceptions.DuplicateResourceException",
                            null,
                            LocaleContextHolder.getLocale()
                    )
            );
        }

        Country country = countryMapper.toEntity(request);
        country = countryRepository.save(country);
        return countryMapper.toDetailResponse(country);
    }
}
