package com.pachedev.library.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.pachedev.library.dto.error.ApiErrorResponse;
import com.pachedev.library.dto.error.ValidationErrorResponse;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ApiErrorResponse> handleResourceNotFound(ResourceNotFoundException e,
                        HttpServletRequest request) {

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                                buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage(), request.getRequestURI()));
        }

        @ExceptionHandler(DuplicateResourceException.class)
        public ResponseEntity<ApiErrorResponse> handleDuplicateResourceException(DuplicateResourceException e,
                        HttpServletRequest request) {

                return ResponseEntity.status(HttpStatus.CONFLICT)
                                .body(buildErrorResponse(HttpStatus.CONFLICT, e.getMessage(), request.getRequestURI()));
        }

        @ExceptionHandler(BusinessRuleException.class)
        public ResponseEntity<ApiErrorResponse> handleBusinessRuleException(BusinessRuleException e,
                        HttpServletRequest request) {

                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body(buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage(),
                                                request.getRequestURI()));
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(
                        MethodArgumentNotValidException e,
                        HttpServletRequest request) {

                Map<String, String> validationErrors = new HashMap<>();

                for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
                        validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
                }

                ValidationErrorResponse errorResponse = new ValidationErrorResponse(
                                LocalDateTime.now(),
                                HttpStatus.BAD_REQUEST.value(),
                                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                                "Validation failed",
                                request.getRequestURI(),
                                validationErrors);

                return ResponseEntity.badRequest().body(errorResponse);
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiErrorResponse> handleGenericException(
                        Exception e,
                        HttpServletRequest request) {

                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(buildErrorResponse(
                                                HttpStatus.INTERNAL_SERVER_ERROR,
                                                "Unexpected internal server error",
                                                request.getRequestURI()));
        }

        private ApiErrorResponse buildErrorResponse(HttpStatus status, String message, String path) {
                return new ApiErrorResponse(LocalDateTime.now(), status.value(),
                                status.getReasonPhrase(), message, path);
        }

}
