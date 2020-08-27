package com.github.krzysiek199720.codeclass.course.course.coursedata.parser.exception.response;

import com.github.krzysiek199720.codeclass.course.course.coursedata.parser.ParserState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseDataParserTokenizerErrorResponse {
    private HttpStatus status;
    private ParserState error;
    private int errorPlace;
    private long time;
}
