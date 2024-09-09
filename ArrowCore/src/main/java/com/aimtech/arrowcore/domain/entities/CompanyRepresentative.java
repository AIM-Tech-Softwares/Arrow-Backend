package com.aimtech.arrowcore.domain.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import java.time.OffsetDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@Audited
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_company_representative")
@AuditTable(value = "tb_log_company_representative")
public class CompanyRepresentative {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "internal_id", nullable = false, unique = true)
    private Long internalId;

    @Column(name = "cpf", length = 11, unique = true, nullable = false)
    private String cpf;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone_fixed", length = 15)
    private String phoneFixed;

    @Column(name = "phone_mobile", nullable = false, length = 15)
    private String phoneMobile;

    @Column(name = "email")
    private String email;

    @Column(name = "position", length = 100)
    private String position;

    @ManyToMany(mappedBy = "representatives")
    private Set<Company> companies;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private OffsetDateTime updatedAt;
}
