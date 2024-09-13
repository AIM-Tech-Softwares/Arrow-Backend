package com.aimtech.arrowcore.domain.entities;

import com.aimtech.arrowcore.core.utils.IdGenerator;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.*;
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

    @Column(name = "email", unique = true)
    private String email;

    @NotAudited
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "last_password_change_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastPasswordChangeDate;

    @NotAudited
    @Column(name = "last_failed_login_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastFailedLoginTime;

    @NotAudited
    @Column(name = "last_login")
    private OffsetDateTime lastLogin;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @NotAudited
    @Column(name = "is_first_login", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean isFirstLogin = true;

    @NotAudited
    @Column(name = "is_password_expired", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isPasswordExpired;

    @NotAudited
    @Column(name = "is_account_expired", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isAccountExpired;

    @NotAudited
    @Column(name = "is_account_locked", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isAccountLocked;

    @NotAudited
    @Column(name = "filed_login_attempts")
    private Integer failedLoginAttempts;

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

    @Override
    public boolean isAccountNonExpired() {
        return !this.isAccountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.isAccountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !this.isPasswordExpired;
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
