package com.aimtech.arrowcore.domain.business.usecases.management.streettype_module;

import com.aimtech.arrowcore.domain.business.dto.requests.management.StreetTypeCreateRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.management.StreetTypeDetailResponse;
import com.aimtech.arrowcore.domain.business.mappers.management.StreetTypeMapper;
import com.aimtech.arrowcore.domain.entities.StreetType;
import com.aimtech.arrowcore.domain.repository.StreetTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateStreetTypeService {
    private final StreetTypeRepository streetTypeRepository;
    private final StreetTypeMapper streetTypeMapper;

    @Transactional
    public StreetTypeDetailResponse execute(StreetTypeCreateRequest request) {
        StreetType streetType = streetTypeMapper.toEntity(request);
        streetType = streetTypeRepository.save(streetType);
        return streetTypeMapper.toDetailResponse(streetType);
    }
}
