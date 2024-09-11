package com.aimtech.arrowcore.domain.business.usecases.city_module;

import com.aimtech.arrowcore.domain.business.dto.responses.management.CityDetailResponse;
import com.aimtech.arrowcore.domain.business.mappers.management.CityMapper;
import com.aimtech.arrowcore.domain.entities.City;
import com.aimtech.arrowcore.domain.repository.CityRepository;
import com.aimtech.arrowcore.infrastructure.exceptions.ResourceNotFoundedException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FindCityByIdService {
    private final CityRepository cityRepository;
    private final CityMapper cityMapper;
    private final MessageSource messageSource;

    @Transactional(readOnly = true)
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
