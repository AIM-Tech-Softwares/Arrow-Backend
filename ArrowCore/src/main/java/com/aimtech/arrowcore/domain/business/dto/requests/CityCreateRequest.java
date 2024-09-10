package com.aimtech.arrowcore.domain.business.dto.requests;

import com.aimtech.arrowcore.core.annotation.ValidLatitude;
import com.aimtech.arrowcore.core.annotation.ValidLongitude;
import com.aimtech.arrowcore.domain.entities.State;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CityCreateRequest {

    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    private String cityName;

    @NotBlank(message = "{arrowcore.messages.errors.validation.NotBlank}")
    private String cityCode;

    @NotNull(message = "{arrowcore.messages.errors.validation.NotNull}")
    @ValidLatitude(message = "{arrowcore.errors.validation.custom.ValidLatitude}")
    private BigDecimal latitude;

    @NotNull(message = "{arrowcore.messages.errors.validation.NotNull}")
    @ValidLongitude(message = "{arrowcore.errors.validation.custom.ValidLongitude}")
    private BigDecimal longitude;

    private Boolean isActive;

    @NotNull(message = "{arrowcore.messages.errors.validation.NotNull}")
    private Long stateId;
}
