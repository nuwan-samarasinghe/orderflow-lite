package com.interviewprep.orderflow_lite.controller;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.MDC;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.interviewprep.orderflow_lite.dto.ErrorDto;
import com.interviewprep.orderflow_lite.dto.FieldErrorDto;
import com.interviewprep.orderflow_lite.exception.BaseBusinessException;

import java.time.Instant;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ---------------- BUSINESS EXCEPTION ----------------
    @ExceptionHandler(BaseBusinessException.class)
    public ResponseEntity<ErrorDto> handleBaseBusinessException(
            BaseBusinessException ex,
            HttpServletRequest request
    ) {

        log.warn("Business exception occurred | code={} | message={} | path={} | correlationId={}",
                ex.getCode(), ex.getMessage(), request.getRequestURI(), getCorrelationId());

        ErrorDto response = buildErrorResponse(
                ex.getStatus(),
                ex.getCode(),
                ex.getMessage(),
                request,
                null
        );

        return ResponseEntity.status(ex.getStatus()).body(response);
    }

    // ---------------- VALIDATION ----------------
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {

        List<FieldErrorDto> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::mapFieldError)
                .toList();

        log.warn("Validation failed | path={} | errors={} | correlationId={}",
                request.getRequestURI(), fieldErrors, getCorrelationId());

        ErrorDto response = buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "VALIDATION_FAILED",
                "Request validation failed",
                request,
                fieldErrors
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDto> handleConstraintViolation(
            ConstraintViolationException ex,
            HttpServletRequest request
    ) {

        log.warn("Constraint violation | message={} | path={} | correlationId={}",
                ex.getMessage(), request.getRequestURI(), getCorrelationId());

        ErrorDto response = buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "CONSTRAINT_VIOLATION",
                ex.getMessage(),
                request,
                null
        );

        return ResponseEntity.badRequest().body(response);
    }

    // ---------------- REQUEST ERRORS ----------------
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorDto> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            HttpServletRequest request
    ) {
        String message = "Invalid value for parameter '" + ex.getName() + "': " + ex.getValue();

        log.warn("Type mismatch | param={} | value={} | path={} | correlationId={}",
                ex.getName(), ex.getValue(), request.getRequestURI(), getCorrelationId());

        ErrorDto response = buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "INVALID_PARAMETER_TYPE",
                message,
                request,
                null
        );

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorDto> handleMissingRequestParam(
            MissingServletRequestParameterException ex,
            HttpServletRequest request
    ) {
        String message = "Missing required request parameter: " + ex.getParameterName();

        log.warn("Missing parameter | param={} | path={} | correlationId={}",
                ex.getParameterName(), request.getRequestURI(), getCorrelationId());

        ErrorDto response = buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "MISSING_REQUEST_PARAMETER",
                message,
                request,
                null
        );

        return ResponseEntity.badRequest().body(response);
    }

    // ---------------- HTTP ERRORS ----------------
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorDto> handleMethodNotSupported(
            HttpRequestMethodNotSupportedException ex,
            HttpServletRequest request
    ) {

        log.warn("Method not supported | method={} | path={} | correlationId={}",
                ex.getMethod(), request.getRequestURI(), getCorrelationId());

        ErrorDto response = buildErrorResponse(
                HttpStatus.METHOD_NOT_ALLOWED,
                "METHOD_NOT_ALLOWED",
                ex.getMessage(),
                request,
                null
        );

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErrorDto> handleMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex,
            HttpServletRequest request
    ) {

        log.warn("Unsupported media type | contentType={} | path={} | correlationId={}",
                ex.getContentType(), request.getRequestURI(), getCorrelationId());

        ErrorDto response = buildErrorResponse(
                HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                "UNSUPPORTED_MEDIA_TYPE",
                ex.getMessage(),
                request,
                null
        );

        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(response);
    }

    // ---------------- DATABASE ----------------
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDto> handleDataIntegrityViolation(
            DataIntegrityViolationException ex,
            HttpServletRequest request
    ) {

        log.error("Database constraint violation | path={} | correlationId={}",
                request.getRequestURI(), getCorrelationId(), ex);

        ErrorDto response = buildErrorResponse(
                HttpStatus.CONFLICT,
                "DATA_INTEGRITY_VIOLATION",
                "Database constraint violation occurred",
                request,
                null
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    // ---------------- GENERIC ----------------
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleGenericException(
            Exception ex,
            HttpServletRequest request
    ) {

        log.error("Unexpected error occurred | path={} | correlationId={}",
                request.getRequestURI(), getCorrelationId(), ex);

        ErrorDto response = buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "INTERNAL_SERVER_ERROR",
                "An unexpected error occurred",
                request,
                null
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    // ---------------- HELPERS ----------------
    private FieldErrorDto mapFieldError(FieldError fieldError) {
        return new FieldErrorDto(
                fieldError.getField(),
                fieldError.getRejectedValue(),
                fieldError.getDefaultMessage()
        );
    }

    private ErrorDto buildErrorResponse(
            HttpStatusCode status,
            String code,
            String message,
            HttpServletRequest request,
            List<FieldErrorDto> fieldErrors
    ) {
        ErrorDto response = new ErrorDto();
        response.setTimestamp(Instant.now());
        response.setStatus(status.value());
        response.setError(HttpStatus.valueOf(status.value()).getReasonPhrase());
        response.setCode(code);
        response.setMessage(message);
        response.setPath(request.getRequestURI());
        response.setCorrelationId(getCorrelationId());
        response.setFieldErrors(fieldErrors);
        return response;
    }

    private String getCorrelationId() {
        return MDC.get("correlationId");
    }
}
