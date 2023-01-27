package com.example.backendhome.util;

import com.example.backendhome.util.exception.TokenRefreshException;
import com.example.backendhome.util.exception.UserByContractNumberNotFoundException;
import com.example.backendhome.util.exception.UserExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorMessage> handleTokenRefreshException(HttpServletRequest req, TokenRefreshException ex) {
        return logAndGetErrorInfo(req, ex, ErrorType.TOKEN_REFRESH_EXCEPTION);
    }

    @ExceptionHandler(UserExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorMessage> handleUserExistsException(HttpServletRequest req, UserExistException ex) {
        return logAndGetErrorInfo(req, ex, ErrorType.USER_IS_ALREADY_EXIST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorMessage> handleEntityNotFoundException(HttpServletRequest req, EntityNotFoundException ex) {
        return logAndGetErrorInfo(req, ex, ErrorType.USER_IS_ALREADY_EXIST);
    }
    @ExceptionHandler(UserByContractNumberNotFoundException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorMessage> handleUserByContractNumberNotFoundException(HttpServletRequest req, EntityNotFoundException ex) {
        return logAndGetErrorInfo(req, ex, ErrorType.USER_BY_CONTRACT_NUMBER_NOT_FOUND);
    }

    private ResponseEntity<ErrorMessage> logAndGetErrorInfo(HttpServletRequest req, Exception e, ErrorType errorType) {
        Throwable rootCause = Optional.ofNullable(NestedExceptionUtils.getRootCause(e)).orElse(e);
        log.error(errorType + " at request " + req.getRequestURL(), rootCause);
        return ResponseEntity
                .status(errorType.getStatus())
                .body(new ErrorMessage(
                        req.getContextPath(),
                        errorType,
                        e.getMessage(),
                        LocalDateTime.now()));
    }
}
