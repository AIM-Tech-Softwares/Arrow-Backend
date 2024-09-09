package com.aimtech.arrowcore.infrastructure.exceptions;

public class NoRepresentativesAssignedException extends RuntimeException {
    public NoRepresentativesAssignedException(String message) {
        super(message);
    }

    public NoRepresentativesAssignedException(String message, Throwable cause) {
        super(message, cause);
    }
}
