package com.aimtech.arrowcore.infrastructure.messaging.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateEmailSenderMessage {

    private String userEmail;
    private String firstname;
    private String lastname;
    private String username;
    private String password;
}
