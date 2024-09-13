package com.aimtech.arrowcore.domain.repository;

import com.aimtech.arrowcore.domain.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByExternalId(UUID externalId);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Page<User> findAllByIsActive(Pageable pageable, boolean isActive);
}
