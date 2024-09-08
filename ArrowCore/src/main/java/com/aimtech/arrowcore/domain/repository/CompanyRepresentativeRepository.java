package com.aimtech.arrowcore.domain.repository;

import com.aimtech.arrowcore.domain.entities.CompanyRepresentative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepresentativeRepository extends JpaRepository<CompanyRepresentative, Long> {

    List<CompanyRepresentative> findByCompanies_Cnpj(String cnpj);
    Boolean existsByCpf(String cpf);
}
