package com.aimtech.arrowcore.domain.entities;

import com.aimtech.arrowcore.core.utils.IdGenerator;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, name = "internal_id")
    private Long internalId;

    @Column(nullable = false, unique = true, name = "external_id")
    private String externalId = IdGenerator.generateExternalId();

    @Column(nullable = false, name = "description")
    private String description;

    @Column(nullable = false, name = "is_active")
    private boolean isActive = true;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tb_profile_role",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public void addRole(Role role) {
        this.roles.add(role);
    }
}
