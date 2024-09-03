package com.aimtech.arrowcore.domain.business.dto.requests;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {

    private String firstName;
    private String lastName;
    private String username;

}
