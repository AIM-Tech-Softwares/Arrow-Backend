package com.aimtech.arrowcore.domain.entities;

import com.aimtech.arrowcore.core.utils.IdGenerator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@Audited
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_profile")
@AuditTable(value = "tb_log_profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, name = "internal_id")
    private Long internalId;

    @Column(nullable = false, unique = true, name = "external_id")
    private UUID externalId = IdGenerator.generateExternalId();

    @Column(nullable = false, name = "profile_name", unique = true)
    private String profileName;

    @Column(name = "description")
    private String description;

    @Column(nullable = false, name = "is_active")
    private Boolean isActive = true;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tb_profile_role",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public void addRole(Role role) {
        this.roles.add(role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profile profile = (Profile) o;
        return Objects.equals(internalId, profile.internalId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(internalId);
    }
}
