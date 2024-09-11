package com.aimtech.arrowcore.domain.business.usecases.country_module;

import com.aimtech.arrowcore.domain.business.dto.responses.management.CountryDetailResponse;
import com.aimtech.arrowcore.domain.business.mappers.management.CountryMapper;
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
public class FindCountryByInternalIdService {
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;
    private final MessageSource messageSource;

    @Transactional(readOnly = true)
    public CountryDetailResponse execute(Long internalId) {
        Country country = countryRepository.findById(internalId).orElseThrow(
                () -> new ResourceNotFoundedException(
                        messageSource.getMessage(
                                "arrowcore.exceptions.ResourceNotFoundedException",
                                new Object[]{"Country", "internalId: " + internalId},
                                LocaleContextHolder.getLocale()
                        )
                )
        );
        return countryMapper.toDetailResponse(country);
    }
}
