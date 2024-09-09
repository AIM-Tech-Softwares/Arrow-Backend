package com.aimtech.arrowcore.domain.entities;

import com.aimtech.arrowcore.core.utils.IdGenerator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Builder
@Audited
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_user")
@AuditTable(value = "tb_log_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, name = "internal_id")
    private Long internalId;

    @Column(nullable = false, unique = true, name = "external_id")
    private UUID externalId = IdGenerator.generateExternalId();

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "last_login")
    private OffsetDateTime lastLogin;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Column(name = "is_first_login", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private boolean isFirstLogin = true;

    @ManyToOne
    @JoinColumn(name = "business_group")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private BusinessGroup businessGroup;


    @ManyToMany
    @JoinTable(
            name = "tb_user_profile",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "profile_id")
    )
    @AuditJoinTable(name = "tb_log_user_profile")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Set<Profile> profiles = new HashSet<>();

    public void addProfile(Profile profile) {
        this.profiles.add(profile);
    }

    public boolean hasRole(String roleName) {
        for (Profile profile : profiles) {
            for (Role role : profile.getRoles()) {
                if (role.getAuthority().equals(roleName)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return this.getProfiles().stream()
                .flatMap(profile -> profile.getRoles().stream())
                .map(role -> new SimpleGrantedAuthority(role.getAuthority().toUpperCase()))
                .collect(Collectors.toSet());
    }

    // # TODO: Implementar a lógica para tratar esses casos
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // # TODO: Implementar a lógica para tratar esses casos
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // # TODO: Implementar a lógica para tratar esses casos
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(internalId, user.internalId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(internalId);
    }
}
