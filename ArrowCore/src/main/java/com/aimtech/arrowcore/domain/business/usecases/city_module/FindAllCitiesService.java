package com.aimtech.arrowcore.domain.business.usecases.city_module;

import com.aimtech.arrowcore.domain.business.dto.responses.CitySummaryResponse;
import com.aimtech.arrowcore.domain.business.mappers.CityMapper;
import com.aimtech.arrowcore.domain.entities.City;
import com.aimtech.arrowcore.domain.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindAllCitiesService {
    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    public Page<CitySummaryResponse> execute(Pageable pageable) {
        Page<City> cityPage = cityRepository.findAll(pageable);
        return cityPage.map(cityMapper::toSummaryResponse);
    }
}
