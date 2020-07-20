package com.github.krzysiek199720.codeclass.core.exceptions.handler;


import com.github.krzysiek199720.codeclass.core.exceptions.exception.BadTokenFormatException;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.SessionExpiredException;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.UnauthorizedException;
import com.github.krzysiek199720.codeclass.core.exceptions.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(BadTokenFormatException exc){

        ErrorResponse response = new ErrorResponse(HttpStatus.UNAUTHORIZED, "auth.token.bad_format");

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(UnauthorizedException exc){

        ErrorResponse response = new ErrorResponse(HttpStatus.UNAUTHORIZED, exc.getMessage());

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(SessionExpiredException exc){

        ErrorResponse response = new ErrorResponse(HttpStatus.UNAUTHORIZED, "auth.session.expired");

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }
}
