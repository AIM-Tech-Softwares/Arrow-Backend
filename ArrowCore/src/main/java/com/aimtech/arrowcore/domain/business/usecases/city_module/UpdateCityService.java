package com.aimtech.arrowcore.domain.business.usecases.city_module;

import com.aimtech.arrowcore.domain.business.dto.requests.CityUpdateRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.CityDetailResponse;
import com.aimtech.arrowcore.domain.business.mappers.CityMapper;
import com.aimtech.arrowcore.domain.business.mappers.CityUpdateMapper;
import com.aimtech.arrowcore.domain.entities.City;
import com.aimtech.arrowcore.domain.entities.State;
import com.aimtech.arrowcore.domain.repository.CityRepository;
import com.aimtech.arrowcore.domain.repository.StateRepository;
import com.aimtech.arrowcore.infrastructure.exceptions.ResourceNotFoundedException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateCityService {
    private final CityRepository cityRepository;
    private final StateRepository stateRepository;
    private final CityMapper cityMapper;
    private final CityUpdateMapper cityUpdateMapper;
    private final MessageSource messageSource;

    @Transactional
    public CityDetailResponse execute(CityUpdateRequest request, Long internalId) {
        City city = cityRepository.findById(internalId).orElseThrow(
                () -> new ResourceNotFoundedException(
                        messageSource.getMessage(
                                "arrowcore.exceptions.ResourceNotFoundedException",
                                new Object[]{"City", "internalId: " + internalId},
                                LocaleContextHolder.getLocale()
                        )
                )
        );
        cityUpdateMapper.updateCity(request, city);

        if (request.getStateId() != null && !request.getStateId().equals(city.getState().getInternalId())) {
            State state = stateRepository.findById(request.getStateId()).orElseThrow(
                    () -> new ResourceNotFoundedException(
                            messageSource.getMessage(
                                    "arrowcore.exceptions.ResourceNotFoundedException",
                                    new Object[]{"State", "internalId: " + request.getStateId()},
                                    LocaleContextHolder.getLocale()
                            )
                    )
            );
            city.setState(state);
        }

        city = cityRepository.save(city);
        return cityMapper.toDetailResponse(city);
    }
}
