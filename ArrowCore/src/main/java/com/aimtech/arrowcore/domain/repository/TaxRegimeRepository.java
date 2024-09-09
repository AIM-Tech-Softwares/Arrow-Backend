package com.aimtech.arrowcore.domain.repository;

import com.aimtech.arrowcore.domain.entities.TaxRegime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxRegimeRepository extends JpaRepository<TaxRegime, Long> {
}
