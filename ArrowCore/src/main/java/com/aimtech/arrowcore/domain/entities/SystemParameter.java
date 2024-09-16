package com.aimtech.arrowcore.domain.entities;

import com.aimtech.arrowcore.domain.enums.ParameterType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Entity
@Getter
@Setter
@Builder
@Audited
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_system_parameters")
@AuditTable(value = "tb_log_system_parameters")
public class SystemParameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "param_key", unique = true, nullable = false)
    private String key;

    @Column(name = "param_value")
    private String value;

    @Column(name = "param_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ParameterType type;
}
