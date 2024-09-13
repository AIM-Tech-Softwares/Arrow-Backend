package com.aimtech.arrowcore.infrastructure.handlers;

import com.aimtech.arrowcore.domain.business.dto.responses.errors.CustomErrorResponse;
import com.aimtech.arrowcore.domain.business.dto.responses.errors.ValidationErrorResponse;
import com.aimtech.arrowcore.infrastructure.exceptions.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.OffsetDateTime;

@ControllerAdvice
@RequiredArgsConstructor
public class ControllerExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(ResourceNotFoundedException.class)
    public ResponseEntity<CustomErrorResponse> resourceNotFoundedExceptionHandler(ResourceNotFoundedException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomErrorResponse err = getCustomError(status, ex.getMessage(), request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(UsernameOrPasswordInvalidException.class)
    public ResponseEntity<CustomErrorResponse> usernameOrPasswordInvalidExceptionHandler(UsernameOrPasswordInvalidException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        CustomErrorResponse err = getCustomError(status, ex.getMessage(), request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(NewPasswordSameAsOldException.class)
    public ResponseEntity<CustomErrorResponse> newPasswordSameAsOldExceptionHandler(NewPasswordSameAsOldException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomErrorResponse err = getCustomError(status, ex.getMessage(), request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(OldPasswordDoesNotMatchException.class)
    public ResponseEntity<CustomErrorResponse> oldPasswordDoesNotMatchExceptionHandler(OldPasswordDoesNotMatchException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomErrorResponse err = getCustomError(status, ex.getMessage(), request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(AccountIsBlockedException.class)
    public ResponseEntity<CustomErrorResponse> accountIsBlockedExceptionHandler(AccountIsBlockedException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        CustomErrorResponse err = getCustomError(status, ex.getMessage(), request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(InvalidDomainForTenantException.class)
    public ResponseEntity<CustomErrorResponse> invalidDomainForTenantExceptionHandler(InvalidDomainForTenantException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomErrorResponse err = getCustomError(status, ex.getMessage(), request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<CustomErrorResponse> usernameAlreadyExistsExceptionHandler(UsernameAlreadyExistsException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomErrorResponse err = getCustomError(status, ex.getMessage(), request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DuplicateEntityException.class)
    public ResponseEntity<CustomErrorResponse> duplicateEntityExceptionHandler(DuplicateEntityException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomErrorResponse err = getCustomError(status, ex.getMessage(), request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(CompanyRepresentativeAlreadyRegisteredException.class)
    public ResponseEntity<CustomErrorResponse> companyRepresentativeAlreadyRegisteredExceptionHandler(CompanyRepresentativeAlreadyRegisteredException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomErrorResponse err = getCustomError(status, ex.getMessage(), request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(CompanyHasNoBranchesException.class)
    public ResponseEntity<CustomErrorResponse> companyHasNoBranchesExceptionHandler(CompanyHasNoBranchesException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomErrorResponse err = getCustomError(status, ex.getMessage(), request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(CompanyCannotBeItsOwnSubsidiaryException.class)
    public ResponseEntity<CustomErrorResponse> companyCannotBeItsOwnSubsidiaryExceptionHandler(CompanyCannotBeItsOwnSubsidiaryException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomErrorResponse err = getCustomError(status, ex.getMessage(), request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<CustomErrorResponse> duplicateResourceExceptionHandler(DuplicateResourceException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomErrorResponse err = getCustomError(status, ex.getMessage(), request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ResourceNotAssociatedException.class)
    public ResponseEntity<CustomErrorResponse> resourceNotAssociatedExceptionHandler(ResourceNotAssociatedException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomErrorResponse err = getCustomError(status, ex.getMessage(), request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(NoRepresentativesAssignedException.class)
    public ResponseEntity<CustomErrorResponse> noRepresentativesAssignedExceptionHandler(NoRepresentativesAssignedException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomErrorResponse err = getCustomError(status, ex.getMessage(), request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(InvalidEnumValueException.class)
    public ResponseEntity<CustomErrorResponse> illegalArgumentExceptionHandler(InvalidEnumValueException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomErrorResponse err = getCustomError(status, ex.getMessage(), request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(CompanyAlreadyRegisteredException.class)
    public ResponseEntity<CustomErrorResponse> companyAlreadyRegisteredExceptionHandler(CompanyAlreadyRegisteredException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomErrorResponse err = getCustomError(status, ex.getMessage(), request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(InvalidTenantDomainException.class)
    public ResponseEntity<CustomErrorResponse> invalidTenantDomainExceptionHandler(InvalidTenantDomainException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        CustomErrorResponse err = getCustomError(status, ex.getMessage(), request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(InvalidPasswordRecoverTokenException.class)
    public ResponseEntity<CustomErrorResponse> invalidPasswordRecoverTokenExceptionHandler(InvalidPasswordRecoverTokenException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        CustomErrorResponse err = getCustomError(status, ex.getMessage(), request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(InvalidDataAccessResourceUsageException.class)
    public ResponseEntity<CustomErrorResponse> invalidDataAccessResourceUsageException(InvalidDataAccessResourceUsageException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.PRECONDITION_FAILED;
        String customMessageException = "Please contact our support team for more information.";
        CustomErrorResponse err = getCustomError(status, customMessageException, request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<CustomErrorResponse> noResourceFoundExceptionHandler(NoResourceFoundException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        CustomErrorResponse err = getCustomError(status, ex.getMessage(), request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<CustomErrorResponse> methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        String customMessageException = messageSource.getMessage(
                "arrowcore.exceptions.MethodArgumentTypeMismatchException",
                new Object[]{ex.getValue(), ex.getName()},
                LocaleContextHolder.getLocale()
        );

        CustomErrorResponse err = getCustomError(status, customMessageException, request);
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationErrorResponse err = new ValidationErrorResponse(OffsetDateTime.now(), status.value(), status.getReasonPhrase(),"Invalid data sent", request.getRequestURI());

        for (FieldError f: ex.getBindingResult().getFieldErrors()) {
            err.addError(f.getField(), f.getDefaultMessage());
        }

        return ResponseEntity.status(status).body(err);
    }

    private static CustomErrorResponse getCustomError(HttpStatus status, String ex, HttpServletRequest request) {
        return CustomErrorResponse.builder()
                .timestamp(OffsetDateTime.now())
                .httpStatus(status.value())
                .httpError(status.getReasonPhrase())
                .error(ex)
                .path(request.getRequestURI())
                .build();
    }
}
