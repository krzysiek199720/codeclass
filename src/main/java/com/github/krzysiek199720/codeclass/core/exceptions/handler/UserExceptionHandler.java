package com.github.krzysiek199720.codeclass.core.exceptions.handler;

import com.github.krzysiek199720.codeclass.core.exceptions.exception.EmailTakenException;
import com.github.krzysiek199720.codeclass.core.exceptions.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(EmailTakenException exc){

        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST, "auth.user.email.taken");

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
