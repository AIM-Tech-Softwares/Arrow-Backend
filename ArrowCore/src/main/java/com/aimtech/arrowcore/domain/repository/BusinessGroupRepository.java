package com.aimtech.arrowcore.domain.repository;

import com.aimtech.arrowcore.domain.entities.BusinessGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessGroupRepository extends JpaRepository<BusinessGroup, Long> {
}
