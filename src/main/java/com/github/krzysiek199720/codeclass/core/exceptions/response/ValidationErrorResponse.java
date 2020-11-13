package com.github.krzysiek199720.codeclass.core.exceptions.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
@Setter
public class ValidationErrorResponse extends ErrorResponse{
    private Map<String, String> errors;

    public ValidationErrorResponse(HttpStatus status, String value, Map<String, String> errors) {
        super(status, value);
        this.errors = errors;
    }
}
