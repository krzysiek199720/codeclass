package com.github.krzysiek199720.codeclass.course.course.coursedata.parser.exception.handler;


import com.github.krzysiek199720.codeclass.course.course.coursedata.parser.exception.exception.CourseDataParserParseErrorException;
import com.github.krzysiek199720.codeclass.course.course.coursedata.parser.exception.exception.CourseDataParserTokenizerErrorException;
import com.github.krzysiek199720.codeclass.course.course.coursedata.parser.exception.response.CourseDataParserParseErrorResponse;
import com.github.krzysiek199720.codeclass.course.course.coursedata.parser.exception.response.CourseDataParserTokenizerErrorResponse;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Order(0)
public class CourseDataParserExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<CourseDataParserTokenizerErrorResponse> handleException(CourseDataParserTokenizerErrorException exc){

        CourseDataParserTokenizerErrorResponse response = new CourseDataParserTokenizerErrorResponse(
                HttpStatus.BAD_REQUEST,
                exc.parserState,
                exc.errorPlace,
                System.currentTimeMillis()
                );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<CourseDataParserParseErrorResponse> handleException(CourseDataParserParseErrorException exc){

        CourseDataParserParseErrorResponse response = new CourseDataParserParseErrorResponse(
                HttpStatus.BAD_REQUEST,
                exc.parserResultState,
                exc.errorPlace,
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
