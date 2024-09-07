package com.aimtech.arrowcore.domain.business.usecases.company_module;

import com.aimtech.arrowcore.core.utils.AuthUtils;
import com.aimtech.arrowcore.core.utils.IdGenerator;
import com.aimtech.arrowcore.domain.business.dto.requests.CompanyRegisterRequest;
import com.aimtech.arrowcore.domain.business.dto.responses.CompanyDetailResponse;
import com.aimtech.arrowcore.domain.business.mappers.CompanyMapper;
import com.aimtech.arrowcore.domain.business.usecases.auth_module.FindBusinessGroupByUsernameDomainService;
import com.aimtech.arrowcore.domain.entities.*;
import com.aimtech.arrowcore.domain.repository.CityRepository;
import com.aimtech.arrowcore.domain.repository.CompanyRepository;
import com.aimtech.arrowcore.domain.repository.StreetTypeRepository;
import com.aimtech.arrowcore.domain.repository.TaxRegimeRepository;
import com.aimtech.arrowcore.infrastructure.exceptions.CompanyAlreadyRegisteredException;
import com.aimtech.arrowcore.infrastructure.exceptions.ResourceNotFoundedException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateCompanyService {
    private final CompanyRepository companyRepository;
    private final StreetTypeRepository streetTypeRepository;
    private final CityRepository cityRepository;
    private final TaxRegimeRepository taxRegimeRepository;
    private final CompanyMapper companyMapper;
    private final MessageSource messageSource;
    private final AuthUtils authUtils;
    private final FindBusinessGroupByUsernameDomainService findBusinessGroupByUsernameDomainService;

    @Transactional
    public CompanyDetailResponse execute(CompanyRegisterRequest request) {
        if (companyRepository.existsByCnpj(request.getCnpj())) {
            throw new CompanyAlreadyRegisteredException(
                    messageSource.getMessage(
                            "arrowcore.exceptions.CompanyAlreadyRegisteredException",
                            null,
                            LocaleContextHolder.getLocale()
                    )
            );
        }
        Company company = companyMapper.toEntity(request);
        company.setRepresentatives(new HashSet<>());
        User currentUser = authUtils.getCurrentUser();

        TaxRegime taxRegime = taxRegimeRepository.findById(request.getTaxRegimeId()).orElseThrow(
                () -> new ResourceNotFoundedException(
                        messageSource.getMessage(
                                "arrowcore.exceptions.ResourceNotFoundedException",
                                new Object[]{"TaxRegime", "ID: " + request.getTaxRegimeId()},
                                LocaleContextHolder.getLocale()
                        )
                )
        );
        StreetType streetType = streetTypeRepository.findById(request.getStreetTypeId()).orElseThrow(
                () -> new ResourceNotFoundedException(
                        messageSource.getMessage(
                                "arrowcore.exceptions.ResourceNotFoundedException",
                                new Object[]{"StreetType", "ID: " + request.getStreetTypeId()},
                                LocaleContextHolder.getLocale()
                        )
                )
        );
        City city = cityRepository.findById(request.getCityId()).orElseThrow(
                () -> new ResourceNotFoundedException(
                        messageSource.getMessage(
                                "arrowcore.exceptions.ResourceNotFoundedException",
                                new Object[]{"City", "ID: " + request.getCityId()},
                                LocaleContextHolder.getLocale()
                        )
                )
        );

        if (request.getParentCompanyId() != null) {
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
        }

        company.setTaxRegime(taxRegime);
        company.setStreetType(streetType);
        company.setCity(city);
        company.setBusinessGroup(currentUser.getBusinessGroup());
        company.setExternalId(IdGenerator.generateExternalId());
        company.setCreatedAt(OffsetDateTime.now());

        Company inserted = companyRepository.save(company);

        return companyMapper.toDetailResponse(inserted);
    }
}
