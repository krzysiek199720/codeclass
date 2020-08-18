package com.github.krzysiek199720.codeclass.course.course.coursedata.parser;

public enum ParserResultState {
    UNKNOWN,
    SUCCESS,
    ERROR_INDENT,
    ERROR_UNEXPECTED_TAG,
    ERROR_MISSING_CODE_END,
    ERROR_MISSING_LINE,
    ERROR_MISSING_LINE_END,
    ERROR_MISSING_ELEMENT_END,
    ERROR_MISSING_ELEMENT_DATA,
    ERROR_NO_DATA
}
