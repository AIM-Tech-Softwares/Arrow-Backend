package com.aimtech.arrowcore.infrastructure.exceptions;

public class InvalidTenantDomainException extends RuntimeException {
    public InvalidTenantDomainException(String message) {
        super(message);
    }
}
