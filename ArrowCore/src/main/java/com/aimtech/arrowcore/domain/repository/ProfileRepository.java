package com.aimtech.arrowcore.domain.repository;

import com.aimtech.arrowcore.domain.entities.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByExternalId(UUID externalId);

    Page<Profile> findAllByIsActive(Pageable page, boolean isActive);
}
