package com.aimtech.arrowcore.domain.repository;

import com.aimtech.arrowcore.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
