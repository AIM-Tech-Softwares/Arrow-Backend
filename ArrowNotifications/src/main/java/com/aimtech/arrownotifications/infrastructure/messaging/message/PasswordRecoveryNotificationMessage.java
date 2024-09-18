package com.aimtech.arrownotifications.infrastructure.messaging.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordRecoveryNotificationMessage {

    private String fistName;
    private String lastName;
    private String tenantDomain;
    private String redirectUrl;
    private Date expirationDate;
    private String email;
}
