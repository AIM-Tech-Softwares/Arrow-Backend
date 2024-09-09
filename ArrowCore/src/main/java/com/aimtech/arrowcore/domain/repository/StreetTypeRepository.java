package com.aimtech.arrowcore.domain.repository;

import com.aimtech.arrowcore.domain.entities.StreetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StreetTypeRepository extends JpaRepository<StreetType, Long> {
}
