package com.aimtech.arrowcore.domain.repository;

import com.aimtech.arrowcore.domain.entities.BusinessGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusinessGroupRepository extends JpaRepository<BusinessGroup, Long> {

    @Query("SELECT b.tenantDomain FROM BusinessGroup b JOIN b.users u WHERE u.username = :username")
    Optional<String> searchTenantDomainByUsername(String username);

    Optional<BusinessGroup> findByTenantDomain(String tenantDomain);
}
