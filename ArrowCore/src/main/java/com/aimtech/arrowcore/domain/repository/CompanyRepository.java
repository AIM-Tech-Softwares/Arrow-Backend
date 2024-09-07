package com.aimtech.arrowcore.domain.repository;

import com.aimtech.arrowcore.domain.entities.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Boolean existsByCnpj(String cnpj);
}
