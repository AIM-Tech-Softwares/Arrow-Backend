package com.aimtech.arrowcore.domain.business.usecases.city_module;

import com.aimtech.arrowcore.domain.business.dto.responses.CityDetailResponse;
import com.aimtech.arrowcore.domain.business.mappers.CityMapper;
import com.aimtech.arrowcore.domain.entities.City;
import com.aimtech.arrowcore.domain.repository.CityRepository;
import com.aimtech.arrowcore.infrastructure.exceptions.ResourceNotFoundedException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindCityByIdService {
    private final CityRepository cityRepository;
    private final CityMapper cityMapper;
    private final MessageSource messageSource;

    public CityDetailResponse execute(Long internalId) {
        City city = cityRepository.findById(internalId).orElseThrow(
                () -> new ResourceNotFoundedException(
                        messageSource.getMessage(
                                "arrowcore.exceptions.ResourceNotFoundedException",
                                new Object[]{"City", "internalId: " + internalId},
                                LocaleContextHolder.getLocale()
                        )
                )
        );
        return cityMapper.toDetailResponse(city);
    }
}
