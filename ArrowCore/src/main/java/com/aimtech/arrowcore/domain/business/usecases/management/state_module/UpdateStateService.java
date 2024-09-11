package com.aimtech.arrowcore.domain.business.usecases.management.state_module;

import com.aimtech.arrowcore.domain.business.dto.requests.management.StateUpdateRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.management.StateDetailResponse;
import com.aimtech.arrowcore.domain.business.mappers.management.StateMapper;
import com.aimtech.arrowcore.domain.business.mappers.management.StateUpdateMapper;
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
public class UpdateStateService {
    private final StateRepository stateRepository;
    private final CountryRepository countryRepository;
    private final StateMapper stateMapper;
    private final StateUpdateMapper stateUpdateMapper;
    private final MessageSource messageSource;

    @Transactional
    public StateDetailResponse execute(StateUpdateRequest request, Long stateId) {
        State state = stateRepository.findById(stateId).orElseThrow(
                () -> new ResourceNotFoundedException(
                        messageSource.getMessage(
                                "arrowcore.exceptions.ResourceNotFoundedException",
                                new Object[]{"State", "internalId: " + stateId},
                                LocaleContextHolder.getLocale()
                        )
                )
        );

        if (request.getCountryId() != null && !request.getCountryId().equals(state.getCountry().getInternalId())) {
            Country country = countryRepository.findById(request.getCountryId()).orElseThrow(
                    () -> new ResourceNotFoundedException(
                            messageSource.getMessage(
                                    "arrowcore.exceptions.ResourceNotFoundedException",
                                    new Object[]{"Country", "countryId: " + request.getCountryId()},
                                    LocaleContextHolder.getLocale()
                            )
                    )
            );
            state.setCountry(country);
        }

        stateUpdateMapper.updateState(request, state);
        state = stateRepository.save(state);
        return stateMapper.toDetailResponse(state);
    }
}
