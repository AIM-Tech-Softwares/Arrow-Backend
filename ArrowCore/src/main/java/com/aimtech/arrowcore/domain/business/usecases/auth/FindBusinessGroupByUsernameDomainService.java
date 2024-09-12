package com.aimtech.arrowcore.domain.business.usecases.auth;

import com.aimtech.arrowcore.domain.business.dto.responses.admin.BusinessGroupResponse;
import com.aimtech.arrowcore.domain.business.mappers.admin.BusinessGroupMapper;
import com.aimtech.arrowcore.domain.entities.BusinessGroup;
import com.aimtech.arrowcore.domain.repository.BusinessGroupRepository;
import com.aimtech.arrowcore.infrastructure.exceptions.InvalidTenantDomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FindBusinessGroupByUsernameDomainService {
    private final BusinessGroupRepository businessGroupRepository;
    private final BusinessGroupMapper businessGroupMapper;
    private final MessageSource messageSource;

    @Transactional(readOnly = true)
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
