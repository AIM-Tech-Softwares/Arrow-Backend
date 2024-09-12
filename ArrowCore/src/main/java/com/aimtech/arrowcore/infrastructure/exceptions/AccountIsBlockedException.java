package com.aimtech.arrowcore.infrastructure.exceptions;

public class AccountIsBlockedException extends RuntimeException {
    public AccountIsBlockedException(String message) {
        super(message);
    }

    public AccountIsBlockedException(String message, Throwable cause) {
      super(message, cause);
    }
}
