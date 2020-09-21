package com.github.krzysiek199720.codeclass.course.coursedata.parser.exception.exception;

import com.github.krzysiek199720.codeclass.course.coursedata.parser.ParserState;

public class CourseDataParserTokenizerErrorException extends RuntimeException{

    public ParserState parserState;
    public int errorPlace;

    public CourseDataParserTokenizerErrorException(ParserState parserState, int errorPlace){
        this.parserState = parserState;
        this.errorPlace = errorPlace;
    }
}
