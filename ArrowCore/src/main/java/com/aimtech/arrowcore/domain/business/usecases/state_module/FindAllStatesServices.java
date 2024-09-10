package com.aimtech.arrowcore.domain.business.usecases.state_module;

import com.aimtech.arrowcore.domain.business.dto.responses.StateDetailResponse;
import com.aimtech.arrowcore.domain.business.mappers.StateMapper;
import com.aimtech.arrowcore.domain.entities.State;
import com.aimtech.arrowcore.domain.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindAllStatesServices {
    private final StateRepository stateRepository;
    private final StateMapper stateMapper;


    public Page<StateDetailResponse> execute(Pageable pageable) {
        Page<State> statePage = stateRepository.findAll(pageable);
        return statePage.map(stateMapper::toDetailResponse);
    }
}
