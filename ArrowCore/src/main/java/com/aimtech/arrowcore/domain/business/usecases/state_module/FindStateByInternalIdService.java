package com.aimtech.arrowcore.domain.business.usecases.state_module;

import com.aimtech.arrowcore.domain.business.dto.responses.management.StateDetailResponse;
import com.aimtech.arrowcore.domain.business.mappers.management.StateMapper;
import com.aimtech.arrowcore.domain.entities.State;
import com.aimtech.arrowcore.domain.repository.StateRepository;
import com.aimtech.arrowcore.infrastructure.exceptions.ResourceNotFoundedException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FindStateByInternalIdService {
    private final StateRepository stateRepository;
    private final StateMapper stateMapper;
    private final MessageSource messageSource;

    @Transactional(readOnly = true)
    public StateDetailResponse execute(Long id) {
        State state = stateRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundedException(
                        messageSource.getMessage(
                                "arrowcore.exceptions.ResourceNotFoundedException",
                                new Object[]{"State", "internalId: " + id},
                                LocaleContextHolder.getLocale()
                        )
                )
        );

        return stateMapper.toDetailResponse(state);
    }
}
