package com.aimtech.arrowcore.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_tax_regime")
public class TaxRegime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "internal_id", nullable = false, unique = true)
    private Long internalId;

    @Column(name = "tax_regime_name", nullable = false)
    private String taxRegimeName;

    @Column(name = "is_active", nullable = false, columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean isActive;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaxRegime taxRegime = (TaxRegime) o;
        return Objects.equals(internalId, taxRegime.internalId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(internalId);
    }
}
