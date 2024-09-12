package com.aimtech.arrowcore.domain.business.usecases.management.state_module;

import com.aimtech.arrowcore.domain.business.dto.requests.management.StateCreateRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.management.StateDetailResponse;
import com.aimtech.arrowcore.domain.business.mappers.management.StateMapper;
import com.aimtech.arrowcore.domain.entities.Country;
import com.aimtech.arrowcore.domain.entities.State;
import com.aimtech.arrowcore.domain.repository.CountryRepository;
import com.aimtech.arrowcore.domain.repository.StateRepository;
import com.aimtech.arrowcore.infrastructure.exceptions.ResourceNotFoundedException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateStateService {
    private final StateRepository stateRepository;
    private final CountryRepository countryRepository;
    private final StateMapper stateMapper;
    private final MessageSource messageSource;

    @Transactional
    public StateDetailResponse execute(StateCreateRequest request) {
        Country country = countryRepository.findById(request.getCountryId()).orElseThrow(
                () -> new ResourceNotFoundedException(
                        messageSource.getMessage(
                                "arrowcore.exceptions.ResourceNotFoundedException",
                                new Object[]{"Country", "countryId: " + request.getCountryId()},
                                LocaleContextHolder.getLocale()
                        )
                )
        );
        State state = stateMapper.toEntity(request);
        state.setCountry(country);
        state = stateRepository.save(state);
        return stateMapper.toDetailResponse(state);
    }
}
