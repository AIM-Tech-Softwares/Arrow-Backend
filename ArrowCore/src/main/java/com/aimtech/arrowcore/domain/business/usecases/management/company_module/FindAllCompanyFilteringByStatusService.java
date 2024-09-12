package com.aimtech.arrowcore.domain.business.usecases.management.company_module;

import com.aimtech.arrowcore.domain.business.dto.responses.management.CompanySummaryResponse;
import com.aimtech.arrowcore.domain.business.mappers.management.CompanyMapper;
import com.aimtech.arrowcore.domain.entities.Company;
import com.aimtech.arrowcore.domain.enums.FilterStatusEnum;
import com.aimtech.arrowcore.domain.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FindAllCompanyFilteringByStatusService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Transactional(readOnly = true)
    public Page<CompanySummaryResponse> execute(Pageable pageable, FilterStatusEnum status) {
        Page<Company> companyPage;

        switch (status) {
            case ENABLED -> companyPage = companyRepository.findAllByIsActive(pageable, true);
            case DISABLED -> companyPage = companyRepository.findAllByIsActive(pageable, false);
            default -> companyPage = companyRepository.findAll(pageable);
        }

        return companyPage.map(companyMapper::toSummaryResponse);
    }
}
