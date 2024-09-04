package com.aimtech.arrowcore.domain.entities;

import com.aimtech.arrowcore.core.utils.IdGenerator;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, name = "internal_id")
    private Long internalId;

    @Column(nullable = false, unique = true, name = "external_id")
    private String externalId = IdGenerator.generateExternalId();

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

    @ManyToOne
    @JoinColumn(name = "business_group")
    private BusinessGroup businessGroup;


    @ManyToMany
    @JoinTable(
            name = "tb_user_profile",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "profile_id")
    )
    private Set<Profile> profiles = new HashSet<>();


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

}
