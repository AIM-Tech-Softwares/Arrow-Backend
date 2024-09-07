package com.aimtech.arrowcore.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_state")
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "internal_id", nullable = false, unique = true)
    private Long internalId;

    @Column(name = "state_name", nullable = false, length = 100)
    private String stateName;

    @Column(name = "state_code", nullable = false, length = 2)
    private String stateCode;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @OneToMany(mappedBy = "state")
    private Set<City> cities;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Objects.equals(internalId, state.internalId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(internalId);
    }
}
