package com.aimtech.arrowcore.domain.business.usecases.state_module;

import com.aimtech.arrowcore.domain.business.dto.requests.StateCreateRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.StateDetailResponse;
import com.aimtech.arrowcore.domain.business.mappers.StateMapper;
import com.aimtech.arrowcore.domain.entities.Country;
import com.aimtech.arrowcore.domain.entities.State;
import com.aimtech.arrowcore.domain.repository.CountryRepository;
import com.aimtech.arrowcore.domain.repository.StateRepository;
import com.aimtech.arrowcore.infrastructure.exceptions.ResourceNotFoundedException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateStateService {
    private final StateRepository stateRepository;
    private final CountryRepository countryRepository;
    private final StateMapper stateMapper;
    private final MessageSource messageSource;

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
