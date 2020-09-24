package com.github.krzysiek199720.codeclass.core.exceptions.handler;

import com.github.krzysiek199720.codeclass.core.exceptions.exception.NotFoundException;
import com.github.krzysiek199720.codeclass.core.exceptions.response.ErrorResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order
public class MainExceptionHandler {

    @Value("${codeclass.debug:false}")
    private boolean debugModeEnabled;


    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception exc) throws Exception {

        if(debugModeEnabled)
            throw exc;

        ErrorResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "internal_server_error");

        return new ResponseEntity<>(response, response.getStatus());
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(NotFoundException exc){

        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND, exc.getMessage());

        return new ResponseEntity<>(response, response.getStatus());
    }
}
