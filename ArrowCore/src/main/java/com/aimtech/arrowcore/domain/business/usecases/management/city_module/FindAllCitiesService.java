package com.aimtech.arrowcore.domain.business.usecases.management.city_module;

import com.aimtech.arrowcore.domain.business.dto.responses.management.CitySummaryResponse;
import com.aimtech.arrowcore.domain.business.mappers.management.CityMapper;
import com.aimtech.arrowcore.domain.entities.City;
import com.aimtech.arrowcore.domain.enums.FilterStatusEnum;
import com.aimtech.arrowcore.domain.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FindAllCitiesService {
    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    @Transactional(readOnly = true)
    public Page<CitySummaryResponse> execute(Pageable pageable, FilterStatusEnum status) {
        Page<City> cityPage;

        switch (status) {
            case ENABLED -> cityPage = cityRepository.findAllByIsActive(pageable, true);
            case DISABLED -> cityPage = cityRepository.findAllByIsActive(pageable, false);
            default -> cityPage = cityRepository.findAll(pageable);
        }

        return cityPage.map(cityMapper::toSummaryResponse);
    }
}
