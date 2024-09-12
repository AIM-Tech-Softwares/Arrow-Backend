package com.aimtech.arrowcore.domain.business.usecases.management.company_module;

import com.aimtech.arrowcore.domain.business.dto.responses.management.CompanySummaryResponse;
import com.aimtech.arrowcore.domain.business.mappers.management.CompanyMapper;
import com.aimtech.arrowcore.domain.entities.Company;
import com.aimtech.arrowcore.domain.repository.CompanyRepository;
import com.aimtech.arrowcore.infrastructure.exceptions.CompanyHasNoBranchesException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FindSubsidiaryCompaniesByParentCnpjService {
    private final CompanyRepository companyRepository;
    private final MessageSource messageSource;
    private final CompanyMapper companyMapper;

    @Transactional(readOnly = true)
    public List<CompanySummaryResponse> execute(String parentCnpj) {
        List<Company> companies = companyRepository.findByParentCompany_Cnpj(parentCnpj);
        if (companies.isEmpty()) {
            throw new CompanyHasNoBranchesException(
                    messageSource.getMessage(
                            "arrowcore.exceptions.CompanyHasNoBranchesException",
                            null,
                            LocaleContextHolder.getLocale()
                    )
            );
        }
        return companies.stream()
                .map(companyMapper::toSummaryResponse)
                .toList();
    }
}
