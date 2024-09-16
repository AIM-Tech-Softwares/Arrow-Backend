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
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.OffsetDateTime;
import java.util.Locale;

@ControllerAdvice
@RequiredArgsConstructor
public class ControllerExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler({
            ResourceNotFoundedException.class,
            EntityNotFoundException.class,
            NoResourceFoundException.class
    })
    public ResponseEntity<CustomErrorResponse> handleNotFound(Exception ex, HttpServletRequest request) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({
            UsernameOrPasswordInvalidException.class,
            AccountIsBlockedException.class,
            LockedException.class,
            DisabledException.class
    })
    public ResponseEntity<CustomErrorResponse> handleUnauthorized(Exception ex, HttpServletRequest request) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.UNAUTHORIZED, request);
    }

    @ExceptionHandler({
            NewPasswordSameAsOldException.class,
            OldPasswordDoesNotMatchException.class,
            InvalidDomainForTenantException.class,
            UsernameAlreadyExistsException.class,
            DuplicateEntityException.class,
            CompanyRepresentativeAlreadyRegisteredException.class,
            CompanyHasNoBranchesException.class,
            CompanyCannotBeItsOwnSubsidiaryException.class,
            DuplicateResourceException.class,
            ResourceNotAssociatedException.class,
            NoRepresentativesAssignedException.class,
            InvalidEnumValueException.class,
            CompanyAlreadyRegisteredException.class,
            InvalidTenantDomainException.class,
    })
    public ResponseEntity<CustomErrorResponse> handleBadRequest(Exception ex, HttpServletRequest request) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(InvalidPasswordRecoverTokenException.class)
    public ResponseEntity<CustomErrorResponse> handleUnprocessableEntity(Exception ex, HttpServletRequest request) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler(InvalidDataAccessResourceUsageException.class)
    public ResponseEntity<CustomErrorResponse> handlePreconditionFailed(InvalidDataAccessResourceUsageException ex, HttpServletRequest request) {
        String customMessageException = "Please contact our support team for more information.";
        return buildResponseEntity(customMessageException, HttpStatus.PRECONDITION_FAILED, request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<CustomErrorResponse> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage("arrowcore.exceptions.MethodArgumentTypeMismatchException",
                new Object[]{ex.getValue(), ex.getName()}, locale);
        return buildResponseEntity(message, HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
        ValidationErrorResponse err = new ValidationErrorResponse(OffsetDateTime.now(), status.value(), status.getReasonPhrase(),
                "Invalid data sent", request.getRequestURI());

        for (FieldError f : ex.getBindingResult().getFieldErrors()) {
            err.addError(f.getField(), f.getDefaultMessage());
        }

        return ResponseEntity.status(status).body(err);
    }

    private ResponseEntity<CustomErrorResponse> buildResponseEntity(String message, HttpStatus status, HttpServletRequest request) {
        CustomErrorResponse err = CustomErrorResponse.builder()
                .timestamp(OffsetDateTime.now())
                .httpStatus(status.value())
                .httpError(status.getReasonPhrase())
                .error(message)
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(status).body(err);
    }
}
