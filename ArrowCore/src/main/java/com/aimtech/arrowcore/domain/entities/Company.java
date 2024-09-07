package com.aimtech.arrowcore.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "internal_id", nullable = false, unique = true)
    private Long internalId;

    @Column(name = "external_id", nullable = false, unique = true)
    private UUID externalId;

    @Column(name = "trade_name", nullable = false)
    private String tradeName;

    @Column(name = "corporate_name", nullable = false)
    private String corporateName;

    @Column(name = "cnpj", nullable = false, unique = true, length = 14)
    private String cnpj;

    @Column(name = "state_registration", nullable = false, length = 20)
    private String stateRegistration;

    @Column(name = "municipal_registration", nullable = false, length = 20)
    private String municipalRegistration;

    @Column(name = "foundation_date", nullable = false)
    private OffsetTime foundationDate;

    @ManyToOne
    @JoinColumn(name = "tax_regime_id", nullable = false)
    private TaxRegime taxRegime;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", nullable = false, length = 15)
    private String phone;

    @ManyToOne
    @JoinColumn(name = "street_type_id")
    private StreetType streetType;

    @Column(name = "street_name", nullable = false)
    private String streetName;

    @Column(name = "street_number", nullable = false, length = 20)
    private String streetNumber;

    @Column(name = "neighborhood", length = 100)
    private String neighborhood;

    @Column(name = "complement", length = 100)
    private String complement;

    @Column(name = "zip_code", nullable = false, length = 8)
    private String zipCode;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @Column(name = "logo_url")
    private String logoUrl;

    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean isActive;

    @ManyToMany
    @JoinTable(
            name = "tb_company_representative_association",
            joinColumns = @JoinColumn(name = "company_id"),
            inverseJoinColumns = @JoinColumn(name = "representative_id")
    )
    private Set<CompanyRepresentative> representatives;

    @ManyToOne
    @JoinColumn(name = "parent_company_id")
    private Company parentCompany;

    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(internalId, company.internalId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(internalId);
    }
}
