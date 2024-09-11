package com.aimtech.arrowcore.domain.business.usecases.country_module;

import com.aimtech.arrowcore.domain.business.dto.responses.CountryDetailResponse;
import com.aimtech.arrowcore.domain.business.mappers.CountryMapper;
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
public class FindCountryByIsoCodeService {
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;
    private final MessageSource messageSource;

    @Transactional(readOnly = true)
    public CountryDetailResponse execute(String isoCode) {
        Country country = countryRepository.findByIsoCode(isoCode.toUpperCase()).orElseThrow(
                () -> new ResourceNotFoundedException(
                        messageSource.getMessage(
                                "arrowcore.exceptions.ResourceNotFoundedException",
                                new Object[]{"Country", "isoCode: " + isoCode.toUpperCase()},
                                LocaleContextHolder.getLocale()
                        )
                )
        );
        return countryMapper.toDetailResponse(country);
    }
}
