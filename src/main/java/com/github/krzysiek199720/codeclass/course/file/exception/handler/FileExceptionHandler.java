package com.github.krzysiek199720.codeclass.course.file.exception.handler;


import com.github.krzysiek199720.codeclass.core.exceptions.response.ErrorResponse;
import com.github.krzysiek199720.codeclass.course.file.exception.exception.CourseFileEmptyException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(0)
public class FileExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(CourseFileEmptyException exc){

        ErrorResponse response = new ErrorResponse(
                HttpStatus.BAD_REQUEST,
                "course.file.empty",
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
