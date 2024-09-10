package com.aimtech.arrowcore.domain.business.usecases.city_module;

import com.aimtech.arrowcore.domain.business.dto.requests.CityCreateRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.CityDetailResponse;
import com.aimtech.arrowcore.domain.business.mappers.CityMapper;
import com.aimtech.arrowcore.domain.entities.City;
import com.aimtech.arrowcore.domain.entities.State;
import com.aimtech.arrowcore.domain.repository.CityRepository;
import com.aimtech.arrowcore.domain.repository.StateRepository;
import com.aimtech.arrowcore.infrastructure.exceptions.ResourceNotFoundedException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateCityService {
    private final CityRepository cityRepository;
    private final StateRepository stateRepository;
    private final CityMapper cityMapper;
    private final MessageSource messageSource;


    public CityDetailResponse execute(CityCreateRequest request) {
        State state = stateRepository.findById(request.getStateId()).orElseThrow(
                () -> new ResourceNotFoundedException(
                        messageSource.getMessage(
                                "arrowcore.exceptions.ResourceNotFoundedException",
                                new Object[]{"State", "internalId: " + request.getStateId()},
                                LocaleContextHolder.getLocale()
                        )
                )
        );
        City city = cityMapper.toEntity(request);
        city.setState(state);
        city = cityRepository.save(city);
        return cityMapper.toDetailResponse(city);
    }
}
