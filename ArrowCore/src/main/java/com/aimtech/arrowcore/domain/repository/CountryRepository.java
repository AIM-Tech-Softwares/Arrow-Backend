package com.aimtech.arrowcore.domain.repository;

import com.aimtech.arrowcore.domain.entities.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    boolean existsByIsoCode(String code);
    Optional<Country> findByIsoCode(String code);
    Page<Country> findAllByIsActive(Pageable pageable, boolean isActive);

}
