package com.aimtech.arrowcore.domain.business.usecases.companyrepresentative_module;

import com.aimtech.arrowcore.domain.business.dto.responses.management.CompanyRepresentativeSummaryResponse;
import com.aimtech.arrowcore.domain.business.mappers.management.CompanyRepresentativeMapper;
import com.aimtech.arrowcore.domain.entities.CompanyRepresentative;
import com.aimtech.arrowcore.domain.repository.CompanyRepository;
import com.aimtech.arrowcore.domain.repository.CompanyRepresentativeRepository;
import com.aimtech.arrowcore.infrastructure.exceptions.ResourceNotFoundedException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindRepresentationsByCompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyRepresentativeRepository companyRepresentativeRepository;
    private final CompanyRepresentativeMapper companyRepresentativeMapper;
    private final MessageSource messageSource;

    @Transactional(readOnly = true)
    public List<CompanyRepresentativeSummaryResponse> execute(String cnpj) {
        if (!companyRepository.existsByCnpj(cnpj)) {
            throw new ResourceNotFoundedException(
                    messageSource.getMessage(
                            "arrowcore.exceptions.ResourceNotFoundedException",
                            new Object[]{"Company", "CNPJ: " + cnpj},
                            LocaleContextHolder.getLocale()
                    )
            );
        }

        List<CompanyRepresentative> representativeList = companyRepresentativeRepository.findByCompanies_Cnpj(cnpj);
        return representativeList.stream()
                .map(companyRepresentativeMapper::toSummaryResponse)
                .toList();
    }
}
