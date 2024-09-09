package com.aimtech.arrowcore.infrastructure.exceptions;

public class CompanyRepresentativeAlreadyRegisteredException extends RuntimeException {
    public CompanyRepresentativeAlreadyRegisteredException(String message) {
        super(message);
    }

    public CompanyRepresentativeAlreadyRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }
}
