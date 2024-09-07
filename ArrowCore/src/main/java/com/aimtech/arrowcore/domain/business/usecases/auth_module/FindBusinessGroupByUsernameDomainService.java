package com.aimtech.arrowcore.domain.business.usecases.auth_module;

import com.aimtech.arrowcore.domain.business.dto.responses.BusinessGroupResponse;
import com.aimtech.arrowcore.domain.business.mappers.BusinessGroupMapper;
import com.aimtech.arrowcore.domain.entities.BusinessGroup;
import com.aimtech.arrowcore.domain.repository.BusinessGroupRepository;
import com.aimtech.arrowcore.infrastructure.exceptions.InvalidTenantDomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindBusinessGroupByUsernameDomainService {
    private final BusinessGroupRepository businessGroupRepository;
    private final BusinessGroupMapper businessGroupMapper;
    private final MessageSource messageSource;

    public BusinessGroupResponse execute(String username) {
        String domain = username.split("@")[1];
        BusinessGroup businessGroup = businessGroupRepository.findByTenantDomain(domain).orElseThrow(
                () -> new InvalidTenantDomainException(
                        messageSource.getMessage(
                                "arrowcore.exceptions.InvalidTenantDomainException",
                                null,
                                LocaleContextHolder.getLocale()
                        )
                )
        );
        return businessGroupMapper.toResponse(businessGroup);
    }
}
