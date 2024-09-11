package com.aimtech.arrowcore.domain.business.usecases.country_module;

import com.aimtech.arrowcore.domain.business.dto.responses.management.CountryDetailResponse;
import com.aimtech.arrowcore.domain.business.mappers.management.CountryMapper;
import com.aimtech.arrowcore.domain.entities.Country;
import com.aimtech.arrowcore.domain.enums.FilterStatusEnum;
import com.aimtech.arrowcore.domain.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FindAllCountryService {
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    @Transactional(readOnly = true)
    public Page<CountryDetailResponse> execute(Pageable pageable, FilterStatusEnum status) {
        Page<Country> countryPage;

        switch (status) {
            case ENABLED -> countryPage = countryRepository.findAllByIsActive(pageable, true);
            case DISABLED -> countryPage = countryRepository.findAllByIsActive(pageable, false);
            default -> countryPage = countryRepository.findAll(pageable);
        }

        return countryPage.map(countryMapper::toDetailResponse);
    }
}
