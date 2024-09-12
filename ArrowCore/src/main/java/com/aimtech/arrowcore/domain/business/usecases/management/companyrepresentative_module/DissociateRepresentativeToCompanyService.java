package com.aimtech.arrowcore.domain.business.usecases.management.companyrepresentative_module;

import com.aimtech.arrowcore.domain.business.dto.requests.management.RepresentativeCompanyRequest;
import com.aimtech.arrowcore.domain.entities.Company;
import com.aimtech.arrowcore.domain.entities.CompanyRepresentative;
import com.aimtech.arrowcore.domain.repository.CompanyRepository;
import com.aimtech.arrowcore.domain.repository.CompanyRepresentativeRepository;
import com.aimtech.arrowcore.infrastructure.exceptions.NoRepresentativesAssignedException;
import com.aimtech.arrowcore.infrastructure.exceptions.ResourceNotAssociatedException;
import com.aimtech.arrowcore.infrastructure.exceptions.ResourceNotFoundedException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DissociateRepresentativeToCompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyRepresentativeRepository companyRepresentativeRepository;
    private final MessageSource messageSource;

    @Transactional
    public void execute(RepresentativeCompanyRequest request, UUID externalCompanyId) {
        Company company = companyRepository.findByExternalId(externalCompanyId).orElseThrow(
                () -> new ResourceNotFoundedException(
                        messageSource.getMessage(
                                "arrowcore.exceptions.ResourceNotFoundedException",
                                new Object[]{"Company", "externalCompanyId: " + externalCompanyId},
                                LocaleContextHolder.getLocale()
                        )
                )
        );
        Set<CompanyRepresentative> representatives = company.getRepresentatives();
        boolean representativeExists = representatives.stream()
                .anyMatch(rep -> rep.getInternalId().equals(request.getRepresentativeId()));

        if (!representativeExists) {
            throw new ResourceNotAssociatedException(
                    messageSource.getMessage(
                            "arrowcore.exceptions.ResourceNotAssociatedException",
                            new Object[]{"Representative", "representativeId: " + request.getRepresentativeId(), "Company: " + company.getCnpj()},
                            LocaleContextHolder.getLocale()
                    )
            );
        }

        if (representatives.size() == 1) {
            throw new NoRepresentativesAssignedException(
                    messageSource.getMessage(
                            "arrowcore.exceptions.NoRepresentativesAssignedException",
                            new Object[]{company.getCnpj()},
                            LocaleContextHolder.getLocale()
                    )
            );
        }

        CompanyRepresentative companyRepresentative = companyRepresentativeRepository
                .findById(request.getRepresentativeId()).orElseThrow(
                        () -> new ResourceNotFoundedException(
                                messageSource.getMessage(
                                        "arrowcore.exceptions.ResourceNotFoundedException",
                                        new Object[]{"CompanyRepresentative", "representativeId: " + request.getRepresentativeId()},
                                        LocaleContextHolder.getLocale()
                                )
                        )
                );
        representatives.remove(companyRepresentative);
        company.setRepresentatives(representatives);
        companyRepository.save(company);
    }
}
