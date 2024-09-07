package com.aimtech.arrowcore.infrastructure.exceptions;

public class CompanyHasNoBranchesException extends RuntimeException {
    public CompanyHasNoBranchesException(String message) {
        super(message);
    }

    public CompanyHasNoBranchesException(String message, Throwable cause) {
      super(message, cause);
    }
}
