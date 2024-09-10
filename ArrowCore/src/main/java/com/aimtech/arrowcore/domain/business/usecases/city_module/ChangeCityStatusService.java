package com.aimtech.arrowcore.domain.business.usecases.city_module;

import com.aimtech.arrowcore.domain.entities.City;
import com.aimtech.arrowcore.domain.repository.CityRepository;
import com.aimtech.arrowcore.infrastructure.exceptions.ResourceNotFoundedException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangeCityStatusService {
    private final CityRepository cityRepository;
    private final MessageSource messageSource;


    public void execute(Long internalId) {
        City city = cityRepository.findById(internalId).orElseThrow(
                () -> new ResourceNotFoundedException(
                        messageSource.getMessage(
                                "arrowcore.exceptions.ResourceNotFoundedException",
                                new Object[]{"City", "internalId: " + internalId},
                                LocaleContextHolder.getLocale()
                        )
                )
        );

        city.setIsActive(!city.getIsActive());
        cityRepository.save(city);
    }
}
