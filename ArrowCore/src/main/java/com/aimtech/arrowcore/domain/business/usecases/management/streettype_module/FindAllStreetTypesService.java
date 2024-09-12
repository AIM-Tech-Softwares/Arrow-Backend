package com.aimtech.arrowcore.domain.business.usecases.management.streettype_module;

import com.aimtech.arrowcore.domain.business.dto.responses.management.StreetTypeSummaryResponse;
import com.aimtech.arrowcore.domain.business.mappers.management.StreetTypeMapper;
import com.aimtech.arrowcore.domain.entities.StreetType;
import com.aimtech.arrowcore.domain.enums.FilterStatusEnum;
import com.aimtech.arrowcore.domain.repository.StreetTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FindAllStreetTypesService {
    private final StreetTypeRepository streetTypeRepository;
    private final StreetTypeMapper streetTypeMapper;
    private final MessageSource messageSource;

    @Transactional(readOnly = true)
    public Page<StreetTypeSummaryResponse> execute(Pageable pageable, FilterStatusEnum status) {
        Page<StreetType> streetTypePage;

        switch (status) {
            case ENABLED -> streetTypePage = streetTypeRepository.findAllByIsActive(pageable, true);
            case DISABLED -> streetTypePage = streetTypeRepository.findAllByIsActive(pageable, false);
            default -> streetTypePage = streetTypeRepository.findAll(pageable);
        }

        return streetTypePage.map(streetTypeMapper::toSummaryResponse);
    }
}
