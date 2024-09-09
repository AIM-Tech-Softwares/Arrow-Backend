package com.aimtech.arrowcore.domain.business.usecases.company_module;

import com.aimtech.arrowcore.domain.business.dto.requests.CompanyUpdateRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.CompanyDetailResponse;
import com.aimtech.arrowcore.domain.business.mappers.CompanyMapper;
import com.aimtech.arrowcore.domain.business.mappers.CompanyUpdateMapper;
import com.aimtech.arrowcore.domain.entities.City;
import com.aimtech.arrowcore.domain.entities.Company;
import com.aimtech.arrowcore.domain.entities.StreetType;
import com.aimtech.arrowcore.domain.entities.TaxRegime;
import com.aimtech.arrowcore.domain.repository.CityRepository;
import com.aimtech.arrowcore.domain.repository.CompanyRepository;
import com.aimtech.arrowcore.domain.repository.StreetTypeRepository;
import com.aimtech.arrowcore.domain.repository.TaxRegimeRepository;
import com.aimtech.arrowcore.infrastructure.exceptions.CompanyCannotBeItsOwnSubsidiaryException;
import com.aimtech.arrowcore.infrastructure.exceptions.ResourceNotFoundedException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateCompanyService {
    private final CompanyRepository companyRepository;
    private final StreetTypeRepository streetTypeRepository;
    private final CityRepository cityRepository;
    private final TaxRegimeRepository taxRegimeRepository;
    private final CompanyMapper companyMapper;
    private final CompanyUpdateMapper companyUpdateMapper;
    private final MessageSource messageSource;

    @Transactional
    public CompanyDetailResponse execute(CompanyUpdateRequest request,  UUID externalId) {
        Company company = companyRepository.findByExternalId(externalId).orElseThrow(
                () -> new ResourceNotFoundedException(
                        messageSource.getMessage(
                                "arrowcore.exceptions.ResourceNotFoundedException",
                                new Object[]{"Company", "external id: " + externalId},
                                LocaleContextHolder.getLocale()
                        )
                )
        );

        if (!Objects.equals(request.getTaxRegimeId(), company.getTaxRegime().getInternalId())) {
            TaxRegime taxRegime = taxRegimeRepository.findById(request.getTaxRegimeId()).orElseThrow(
                    () -> new ResourceNotFoundedException(
                            messageSource.getMessage(
                                    "arrowcore.exceptions.ResourceNotFoundedException",
                                    new Object[]{"TaxRegime", "ID: " + request.getTaxRegimeId()},
                                    LocaleContextHolder.getLocale()
                            )
                    )
            );
            company.setTaxRegime(taxRegime);
        }

        if (!Objects.equals(request.getTaxRegimeId(), company.getTaxRegime().getInternalId())) {
            StreetType streetType = streetTypeRepository.findById(request.getStreetTypeId()).orElseThrow(
                    () -> new ResourceNotFoundedException(
                            messageSource.getMessage(
                                    "arrowcore.exceptions.ResourceNotFoundedException",
                                    new Object[]{"StreetType", "ID: " + request.getStreetTypeId()},
                                    LocaleContextHolder.getLocale()
                            )
                    )
            );
            company.setStreetType(streetType);
        }

        if (!Objects.equals(request.getCityId(), company.getCity().getInternalId())) {
            City city = cityRepository.findById(request.getCityId()).orElseThrow(
                    () -> new ResourceNotFoundedException(
                            messageSource.getMessage(
                                    "arrowcore.exceptions.ResourceNotFoundedException",
                                    new Object[]{"City", "ID: " + request.getCityId()},
                                    LocaleContextHolder.getLocale()
                            )
                    )
            );
            company.setCity(city);
        }

        if (request.getParentCompanyId() != null && !Objects.equals(request.getParentCompanyId(), company.getParentCompany().getInternalId())) {
            if (!request.getParentCompanyId().equals(company.getInternalId())) {
                Company parentCompany = companyRepository.findById(request.getParentCompanyId()).orElseThrow(
                        () -> new ResourceNotFoundedException(
                                messageSource.getMessage(
                                        "arrowcore.exceptions.ResourceNotFoundedException",
                                        new Object[]{"Company", "ID: " + request.getParentCompanyId()},
                                        LocaleContextHolder.getLocale()
                                )
                        )
                );
                company.setParentCompany(parentCompany);
            } else {
                throw new CompanyCannotBeItsOwnSubsidiaryException(
                        messageSource.getMessage(
                                "arrowcore.exceptions.CompanyCannotBeItsOwnSubsidiaryException",
                                null,
                                LocaleContextHolder.getLocale()
                        )
                );
            }
        }


        companyUpdateMapper.updateCompany(request, company);
        Company updated = companyRepository.save(company);
        return companyMapper.toDetailResponse(updated);
    }
}
