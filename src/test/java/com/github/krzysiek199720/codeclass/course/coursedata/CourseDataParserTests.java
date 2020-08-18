package com.github.krzysiek199720.codeclass.course.coursedata;

import com.github.krzysiek199720.codeclass.course.course.coursedata.CourseData;
import com.github.krzysiek199720.codeclass.course.course.coursedata.parser.CourseDataParser;
import com.github.krzysiek199720.codeclass.course.course.coursedata.parser.ParserResultState;
import com.github.krzysiek199720.codeclass.course.course.coursedata.parser.ParserState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.List;

@SpringBootTest
public class CourseDataParserTests {

    @Test
    void parserWorksAllElements(@Autowired CourseDataParser parser) {
        String testCase = "Here is where the text could be inserted. " +
                          "<code> " +
                          "<line indent=\"1\"> " +
                          "Here is where magic happens. " +
                          "<element desc=\"Description of magic\"> " +
                          "Magic is awesome! " +
                          "</element> " +
                          "</line> " +
                          "<line> " +
                          "<element desc=\"Description one\"> " +
                          "One.text " +
                          "<element desc=\"Description one.one\"> " +
                          "One.one.text " +
                          "</element> " +
                          "</element> " +
                          "</line> " +
                          "</code> " +
                          "Just want to say thank you. ";

        parser.parse(testCase);
        Assert.state(parser.getState() == ParserState.SUCCESS, "Parser should end with success");

        List<CourseData> cd = parser.getResults();
        Assert.state(parser.getResultState() == ParserResultState.SUCCESS, "Parser result should end with success");
        Assert.notNull(cd,"Parser results should not be null");
        Assert.notEmpty(cd, "Parser results should not be empty");
    }

    @Test
    void parserCodeInsideCode(@Autowired CourseDataParser parser) {
        String testCase = "<code> <code> </code> </code>";
        parser.parse(testCase);
        Assert.state(parser.getState() == ParserState.SUCCESS, "Parser should end with success");

        List<CourseData> cd = parser.getResults();
        Assert.state(parser.getResultState() == ParserResultState.ERROR_UNEXPECTED_TAG, "Code inside code should not be allowed");
        Assert.isNull(cd,"Parser results should be null on error");
    }

    @Test
    void parserLineInsideLine(@Autowired CourseDataParser parser) {
        String testCase = "<code> <line> <line> </line> </line> </code>";
        parser.parse(testCase);
        Assert.state(parser.getState() == ParserState.SUCCESS, "Parser should end with success");

        List<CourseData> cd = parser.getResults();
        Assert.state(parser.getResultState() == ParserResultState.ERROR_UNEXPECTED_TAG, "Line inside line should not be allowed");
        Assert.isNull(cd,"Parser results should be null on error");
    }

    @Test
    void parserLineWithoutCode(@Autowired CourseDataParser parser) {
        String testCase = "<line> </line>";
        parser.parse(testCase);
        Assert.state(parser.getState() == ParserState.SUCCESS, "Parser should end with success");

        List<CourseData> cd = parser.getResults();
        Assert.state(parser.getResultState() == ParserResultState.ERROR_UNEXPECTED_TAG, "Line outside code should not be allowed");
        Assert.isNull(cd,"Parser results should be null on error");
    }

    @Test
    void parserElementWithoutLine(@Autowired CourseDataParser parser) {
        String testCase = "<code> <element> </element> </code>";
        parser.parse(testCase);
        Assert.state(parser.getState() == ParserState.SUCCESS, "Parser should end with success");

        List<CourseData> cd = parser.getResults();
        Assert.state(parser.getResultState() == ParserResultState.ERROR_UNEXPECTED_TAG, "Element outside line should not be allowed");
        Assert.isNull(cd,"Parser results should be null on error");
    }

    @Test
    void parserElementOutsideCode(@Autowired CourseDataParser parser) {
        String testCase = "<element> </element>";
        parser.parse(testCase);
        Assert.state(parser.getState() == ParserState.SUCCESS, "Parser should end with success");

        List<CourseData> cd = parser.getResults();
        Assert.state(parser.getResultState() == ParserResultState.ERROR_UNEXPECTED_TAG, "Element outside code should not be allowed");
        Assert.isNull(cd,"Parser results should be null on error");
    }

    @Test
    void parserNoCodeEnd(@Autowired CourseDataParser parser) {
        String testCase = "<code>";
        parser.parse(testCase);
        Assert.state(parser.getState() == ParserState.SUCCESS, "Parser should end with success");

        List<CourseData> cd = parser.getResults();
        Assert.state(parser.getResultState() == ParserResultState.ERROR_MISSING_CODE_END, "Code should have an end");
        Assert.isNull(cd,"Parser results should be null on error");
    }

    @Test
    void parserNoLineEnd(@Autowired CourseDataParser parser) {
        String testCase = "<code> <line> </code>";
        parser.parse(testCase);
        Assert.state(parser.getState() == ParserState.SUCCESS, "Parser should end with success");

        List<CourseData> cd = parser.getResults();
        Assert.state(parser.getResultState() == ParserResultState.ERROR_MISSING_LINE_END, "Line should have an end");
        Assert.isNull(cd,"Parser results should be null on error");
    }

    @Test
    void parserNoElementEnd(@Autowired CourseDataParser parser) {
        String testCase = "<code> <line> <element> </line> </code>";
        parser.parse(testCase);
        Assert.state(parser.getState() == ParserState.SUCCESS, "Parser should end with success");

        List<CourseData> cd = parser.getResults();
        Assert.state(parser.getResultState() == ParserResultState.ERROR_MISSING_ELEMENT_END, "Element should have an end");
        Assert.isNull(cd,"Parser results should be null on error");
    }

    @Test
    void parserNoElementEndWithData(@Autowired CourseDataParser parser) {
        String testCase = "<code> <line> <element>ssa </line> </code>";
        parser.parse(testCase);
        Assert.state(parser.getState() == ParserState.SUCCESS, "Parser should end with success");

        List<CourseData> cd = parser.getResults();
        Assert.state(parser.getResultState() == ParserResultState.ERROR_MISSING_ELEMENT_END, "Element should have an end");
        Assert.isNull(cd,"Parser results should be null on error");
    }

    @Test
    void parserNoElementData(@Autowired CourseDataParser parser) {
        String testCase = "<code> <line> <element> </element> </line> </code>";
        parser.parse(testCase);
        Assert.state(parser.getState() == ParserState.SUCCESS, "Parser should end with success");

        List<CourseData> cd = parser.getResults();
        Assert.state(parser.getResultState() == ParserResultState.ERROR_MISSING_ELEMENT_DATA, "Element should have data");
        Assert.isNull(cd,"Parser results should be null on error");
    }

    @Test
    void parserElementDataAtEnd(@Autowired CourseDataParser parser) {
        String testCase = "<code> <line> <element> <element> sa </element> ss</element> </line> </code>";
        parser.parse(testCase);
        Assert.state(parser.getState() == ParserState.SUCCESS, "Parser should end with success");

        List<CourseData> cd = parser.getResults();
        Assert.state(parser.getResultState() == ParserResultState.ERROR_MISSING_ELEMENT_DATA, "Element should have data at front");
        Assert.isNull(cd,"Parser results should be null on error");
    }
}
