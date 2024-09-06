package com.aimtech.arrowcore.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_password_recover")
public class PasswordRecover {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, name = "internal_id")
    private Long internalId;

    @Column(name = "token", nullable = false)
    private String token;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime createdAt;

    @Column(nullable = false)
    private Instant expiration;

    @Column(name = "used_at", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime usedAt;

}
