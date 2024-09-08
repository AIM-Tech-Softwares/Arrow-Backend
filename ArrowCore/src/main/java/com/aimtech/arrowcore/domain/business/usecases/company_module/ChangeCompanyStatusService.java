package com.aimtech.arrowcore.domain.business.usecases.company_module;

import com.aimtech.arrowcore.domain.business.dto.responses.CompanyDetailResponse;
import com.aimtech.arrowcore.domain.business.mappers.CompanyMapper;
import com.aimtech.arrowcore.domain.entities.Company;
import com.aimtech.arrowcore.domain.repository.CompanyRepository;
import com.aimtech.arrowcore.infrastructure.exceptions.ResourceNotFoundedException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChangeCompanyStatusService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final MessageSource messageSource;

    public CompanyDetailResponse execute(UUID externalId) {
        Company company = companyRepository.findByExternalId(externalId).orElseThrow(
                () -> new ResourceNotFoundedException(
                        messageSource.getMessage(
                                "arrowcore.exceptions.ResourceNotFoundedException",
                                new Object[]{"Company", "external id: " + externalId},
                                LocaleContextHolder.getLocale()
                        )
                )
        );

        company.setIsActive(!company.getIsActive());
        Company changed = companyRepository.save(company);
        return companyMapper.toDetailResponse(changed);
    }
}
