package com.aimtech.arrowcore.domain.business.usecases.country_module;

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
public class ChangeCountryStatusService {
    private final CountryRepository countryRepository;
    private final MessageSource messageSource;

    @Transactional
    public void execute(Long internalId) {
        Country country = countryRepository.findById(internalId).orElseThrow(
                () -> new ResourceNotFoundedException(
                        messageSource.getMessage(
                                "arrowcore.exceptions.ResourceNotFoundedException",
                                new Object[]{"Country", "internalId: " + internalId},
                                LocaleContextHolder.getLocale()
                        )
                )
        );

        country.setIsActive(!country.getIsActive());
        countryRepository.save(country);
    }
}
