package com.aimtech.arrowcore.infrastructure.exceptions;

public class CompanyCannotBeItsOwnSubsidiaryException extends RuntimeException {
    public CompanyCannotBeItsOwnSubsidiaryException(String message) {
        super(message);
    }

    public CompanyCannotBeItsOwnSubsidiaryException(String message, Throwable cause) {
        super(message, cause);
    }
}
