package com.github.krzysiek199720.codeclass.course.course.coursedata.parser.exception.exception;

import com.github.krzysiek199720.codeclass.course.course.coursedata.parser.ParserResultState;

public class CourseDataParserParseErrorException extends RuntimeException{

    public ParserResultState parserResultState;
    public int errorPlace;

    public CourseDataParserParseErrorException(ParserResultState parserResultState, int errorPlace){
        this.parserResultState = parserResultState;
        this.errorPlace = errorPlace;
    }
}
