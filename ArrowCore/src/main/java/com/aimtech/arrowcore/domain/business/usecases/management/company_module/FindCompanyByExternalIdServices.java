package com.aimtech.arrowcore.domain.business.usecases.management.company_module;

import com.aimtech.arrowcore.domain.business.dto.responses.management.CompanyDetailResponse;
import com.aimtech.arrowcore.domain.business.mappers.management.CompanyMapper;
import com.aimtech.arrowcore.domain.entities.Company;
import com.aimtech.arrowcore.domain.repository.CompanyRepository;
import com.aimtech.arrowcore.infrastructure.exceptions.ResourceNotFoundedException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FindCompanyByExternalIdServices {
    private final CompanyRepository companyRepository;
    private final MessageSource messageSource;
    private final CompanyMapper companyMapper;

    @Transactional(readOnly = true)
    public CompanyDetailResponse execute(UUID externalId) {
        Company company = companyRepository.findByExternalId(externalId).orElseThrow(
                () -> new ResourceNotFoundedException(
                        messageSource.getMessage(
                                "arrowcore.exceptions.ResourceNotFoundedException",
                                new Object[]{"Company", "public id: " + externalId},
                                LocaleContextHolder.getLocale()
                        )
                )
        );

        return companyMapper.toDetailResponse(company);
    }
}
