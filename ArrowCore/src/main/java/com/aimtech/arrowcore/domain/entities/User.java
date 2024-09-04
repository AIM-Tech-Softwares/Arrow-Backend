package com.aimtech.arrowcore.domain.entities;

import com.aimtech.arrowcore.core.utils.IdGenerator;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.OffsetDateTime;
import java.util.Collection;

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


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "profile_id", referencedColumnName = "internal_id")
    private Profile profile;


    public boolean hasRole(String roleName) {
        for (Role role : profile.getRoles()) {
            if (role.getAuthority().equals(roleName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.profile.getRoles();
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
