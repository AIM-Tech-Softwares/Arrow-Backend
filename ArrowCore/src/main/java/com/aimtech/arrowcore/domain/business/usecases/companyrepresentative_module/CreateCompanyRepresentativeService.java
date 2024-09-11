package com.aimtech.arrowcore.domain.business.usecases.companyrepresentative_module;

import com.aimtech.arrowcore.domain.business.dto.requests.CompanyRepresentativeCreateRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.CompanyRepresentativeDetailResponse;
import com.aimtech.arrowcore.domain.business.mappers.CompanyRepresentativeMapper;
import com.aimtech.arrowcore.domain.entities.Company;
import com.aimtech.arrowcore.domain.entities.CompanyRepresentative;
import com.aimtech.arrowcore.domain.repository.CompanyRepository;
import com.aimtech.arrowcore.domain.repository.CompanyRepresentativeRepository;
import com.aimtech.arrowcore.infrastructure.exceptions.CompanyRepresentativeAlreadyRegisteredException;
import com.aimtech.arrowcore.infrastructure.exceptions.ResourceNotFoundedException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateCompanyRepresentativeService {
    private final CompanyRepository companyRepository;
    private final CompanyRepresentativeRepository companyRepresentativeRepository;
    private final CompanyRepresentativeMapper companyRepresentativeMapper;
    private final MessageSource messageSource;

    @Transactional
    public CompanyRepresentativeDetailResponse execute(
            CompanyRepresentativeCreateRequest request,
            UUID externalCompanyId
    ) {
        Company company = companyRepository.findByExternalId(externalCompanyId).orElseThrow(
                () -> new ResourceNotFoundedException(
                        messageSource.getMessage(
                                "arrowcore.exceptions.ResourceNotFoundedException",
                                new Object[]{"Company", "externalCompanyId: " + externalCompanyId},
                                LocaleContextHolder.getLocale()
                        )
                )
        );
        if (companyRepresentativeRepository.existsByCpf(request.getCpf())) {
            throw new CompanyRepresentativeAlreadyRegisteredException(
                    messageSource.getMessage(
                            "arrowcore.exceptions.CompanyRepresentativeAlreadyRegisteredException",
                            null,
                            LocaleContextHolder.getLocale()
                    )
            );
        }

        CompanyRepresentative companyRepresentative = companyRepresentativeMapper.toEntity(request);
        companyRepresentative.setCompanies(new HashSet<>());
        companyRepresentative.getCompanies().add(company);

        CompanyRepresentative inserted = companyRepresentativeRepository.save(companyRepresentative);
        return companyRepresentativeMapper.toDetailResponse(inserted);
    }
}
