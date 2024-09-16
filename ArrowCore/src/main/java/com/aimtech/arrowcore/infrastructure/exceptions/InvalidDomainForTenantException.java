package com.aimtech.arrowcore.infrastructure.exceptions;

public class InvalidDomainForTenantException extends RuntimeException {
    public InvalidDomainForTenantException(String message) {
        super(message);
    }

    public InvalidDomainForTenantException(String message, Throwable cause) {
        super(message, cause);
    }
}
