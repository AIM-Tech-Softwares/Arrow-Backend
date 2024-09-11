package com.aimtech.arrowcore.domain.business.usecases.management.streettype_module;

import com.aimtech.arrowcore.domain.business.dto.requests.management.StreetTypeUpdateRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.management.StreetTypeDetailResponse;
import com.aimtech.arrowcore.domain.business.mappers.management.StreetTypeMapper;
import com.aimtech.arrowcore.domain.business.mappers.management.StreetTypeUpdateMapper;
import com.aimtech.arrowcore.domain.entities.StreetType;
import com.aimtech.arrowcore.domain.repository.StreetTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateStreetTypeService {
    private final StreetTypeRepository streetTypeRepository;
    private final StreetTypeMapper streetTypeMapper;
    private final StreetTypeUpdateMapper streetTypeUpdateMapper;
    private final MessageSource messageSource;

    @Transactional
    public StreetTypeDetailResponse execute(StreetTypeUpdateRequest request, Long internalId) {
        StreetType streetType = streetTypeRepository.findById(internalId).orElseThrow(
                () -> new RuntimeException(
                        messageSource.getMessage(
                                "arrowcore.exceptions.ResourceNotFoundedException",
                                new Object[]{"StreetType", "internalId: " + internalId},
                                LocaleContextHolder.getLocale()
                        )
                )
        );
        streetTypeUpdateMapper.updateStreetType(request, streetType);
        streetType = streetTypeRepository.save(streetType);
        return streetTypeMapper.toDetailResponse(streetType);
    }
}
