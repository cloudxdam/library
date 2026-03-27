package com.pachedev.library.dto.error;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ValidationErrorResponse extends ApiErrorResponse {

    private Map<String, String> validationErrors;

    public ValidationErrorResponse(LocalDateTime timeStamp, int status, String error, String message, String path,
            Map<String, String> validationErrors) {

        super(timeStamp, status, error, message, path);
        this.validationErrors = validationErrors;
    }

}
