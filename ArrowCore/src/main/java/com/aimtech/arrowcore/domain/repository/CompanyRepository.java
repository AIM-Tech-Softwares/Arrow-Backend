package com.aimtech.arrowcore.domain.repository;

import com.aimtech.arrowcore.domain.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Boolean existsByCnpj(String cnpj);

    Optional<Company> findByExternalId(UUID externalId);

    List<Company> findByParentCompany_Cnpj(String cnpj);
}
