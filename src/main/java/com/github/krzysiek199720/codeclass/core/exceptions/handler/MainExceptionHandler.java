package com.github.krzysiek199720.codeclass.core.exceptions.handler;

import com.github.krzysiek199720.codeclass.core.exceptions.exception.NotFoundException;
import com.github.krzysiek199720.codeclass.core.exceptions.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MainExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exc){

        ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "internal_server_error");

        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(NotFoundException exc){

        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND, exc.getMessage());

        return new ResponseEntity<>(response, response.getStatus());
    }
}
