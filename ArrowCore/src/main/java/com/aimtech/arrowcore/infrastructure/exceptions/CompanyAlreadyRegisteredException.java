package com.aimtech.arrowcore.infrastructure.exceptions;

public class CompanyAlreadyRegisteredException extends RuntimeException {
    public CompanyAlreadyRegisteredException(String message) {
        super(message);
    }

    public CompanyAlreadyRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }
}
