package com.aimtech.arrowcore.domain.entities;

import com.aimtech.arrowcore.core.utils.IdGenerator;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_business_group")
public class BusinessGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, name = "internal_id")
    private Long internalId;

    @Column(nullable = false, unique = true, name = "external_id")
    private String externalId = IdGenerator.generateExternalId();

    @Column(name = "public_name", nullable = false)
    private String publicName;

    @Column(name = "tenant_domain", nullable = false)
    private String tenantDomain;

    @Column(name = "schema_name", nullable = false, unique = true)
    private String schemaName;

    @OneToMany(mappedBy = "businessGroup")
    private List<User> users;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusinessGroup that = (BusinessGroup) o;
        return Objects.equals(internalId, that.internalId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(internalId);
    }
}
