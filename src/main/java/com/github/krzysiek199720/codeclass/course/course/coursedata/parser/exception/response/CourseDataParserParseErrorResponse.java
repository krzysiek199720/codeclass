package com.github.krzysiek199720.codeclass.course.course.coursedata.parser.exception.response;


import com.github.krzysiek199720.codeclass.course.course.coursedata.parser.ParserResultState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseDataParserParseErrorResponse {
    private HttpStatus status;
    private ParserResultState error;
    private int errorPlace;
    private long time;
}
