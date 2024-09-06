package com.aimtech.arrowcore.domain.repository;

import com.aimtech.arrowcore.domain.entities.PasswordRecover;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface PasswordRecoveryRepository extends JpaRepository<PasswordRecover, Long> {

    @Query("""
        SELECT obj FROM PasswordRecover obj
        WHERE obj.token = :token AND obj.expiration > :now AND obj.usedAt IS NULL
    """)
    Optional<PasswordRecover> searchValidToken(String token, Instant now);

    @Query("""
        SELECT obj FROM PasswordRecover obj
        WHERE obj.username = :username AND obj.expiration > :now AND obj.usedAt IS NULL
    """)
    List<PasswordRecover> searchValidTokensFromEmail(String username, Instant now);
}
