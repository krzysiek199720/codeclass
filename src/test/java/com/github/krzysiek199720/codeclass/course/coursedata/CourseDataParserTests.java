package com.github.krzysiek199720.codeclass.course.coursedata;

import com.github.krzysiek199720.codeclass.course.coursedata.parser.CourseDataParser;
import com.github.krzysiek199720.codeclass.course.coursedata.parser.ParserResultState;
import com.github.krzysiek199720.codeclass.course.coursedata.parser.ParserState;
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

        parser.tokenize(testCase);
        Assert.state(parser.getState() == ParserState.SUCCESS, "Parser should end with success");

        List<CourseData> cd = parser.parse();
        Assert.state(parser.getResultState() == ParserResultState.SUCCESS, "Parser result should end with success");
        Assert.notNull(cd,"Parser results should not be null");
        Assert.notEmpty(cd, "Parser results should not be empty");
    }

    @Test
    void parserWorksAllElementsOneline(@Autowired CourseDataParser parser) {
        String testCase = "Here is where the text could be inserted.<code><line indent=\"1\">Here is where magic happens.<element desc=\"Description of magic\">Magic is awesome!</element></line><line><element desc=\"Description one\">One.text<element desc=\"Description one.one\">One.one.text</element></element></line></code>Just want to say thank you.";

        parser.tokenize(testCase);
        Assert.state(parser.getState() == ParserState.SUCCESS, "Parser should end with success");

        List<CourseData> cd = parser.parse();
        Assert.state(parser.getResultState() == ParserResultState.SUCCESS, "Parser result should end with success");
        Assert.notNull(cd,"Parser results should not be null");
        Assert.notEmpty(cd, "Parser results should not be empty");
    }

    @Test
    void parserWorksAllElementsWithLessSign(@Autowired CourseDataParser parser) {
        String testCase = "Here is where the text could be inserted. " +
                "<code> " +
                "<line indent=\"1\"> " +
                "Here is where magic happens. " +
                "<element desc=\"Description of magic\"> " +
                "Magic is awesome\\<>! " +
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

        parser.tokenize(testCase);
        Assert.state(parser.getState() == ParserState.SUCCESS, "Parser should end with success");

        List<CourseData> cd = parser.parse();
        Assert.state(parser.getResultState() == ParserResultState.SUCCESS, "Parser result should end with success");
        Assert.notNull(cd,"Parser results should not be null");
        Assert.notEmpty(cd, "Parser results should not be empty");
    }

    @Test
    void parserWorksAllElementsWithLessSignTwoTimesInARow(@Autowired CourseDataParser parser) {
        String testCase = "Here is where the text could be inserted. " +
                "<code> " +
                "<line indent=\"1\"> " +
                "Here is where magic happens. " +
                "<element desc=\"Description of magic\"> " +
                "Magic is awesome\\<\\<>! " +
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

        parser.tokenize(testCase);
        Assert.state(parser.getState() == ParserState.SUCCESS, "Parser should end with success");

        List<CourseData> cd = parser.parse();
        Assert.state(parser.getResultState() == ParserResultState.SUCCESS, "Parser result should end with success");
        Assert.notNull(cd,"Parser results should not be null");
        Assert.notEmpty(cd, "Parser results should not be empty");
    }

    @Test
    void parserIndentNotNumber(@Autowired CourseDataParser parser) {
        String testCase = "<code> " +
                "<line indent=\"q\"> " +
                "Here is where magic happens. " +
                "<element desc=\"Description of magic\"> " +
                "Magic is awesome\\<\\<>! " +
                "</element> " +
                "</line> " +
                "</code>";

        parser.tokenize(testCase);
        Assert.state(parser.getState() == ParserState.SUCCESS, "Parser should end with success");

        List<CourseData> cd = parser.parse();
        Assert.state(parser.getResultState() == ParserResultState.ERROR_INDENT, "Parser result should end with error");
        Assert.isNull(cd,"Parser results should not be null");
    }

    @Test
    void parserIndentNo(@Autowired CourseDataParser parser) {
        String testCase = "<code> " +
                "<line indent=\"\"> " +
                "Here is where magic happens. " +
                "<element desc=\"Description of magic\"> " +
                "Magic is awesome\\<\\<>! " +
                "</element> " +
                "</line> " +
                "</code>";

        parser.tokenize(testCase);
        Assert.state(parser.getState() == ParserState.SUCCESS, "Parser should end with success");

        List<CourseData> cd = parser.parse();
        Assert.state(parser.getResultState() == ParserResultState.SUCCESS, "Parser result should end with success");
        Assert.notNull(cd,"Parser results should not be null");
    }

    @Test
    void parserDescriptionNo(@Autowired CourseDataParser parser) {
        String testCase = "<code> " +
                "<line indent=\"1\"> " +
                "Here is where magic happens. " +
                "<element desc=\"\"> " +
                "Magic is awesome\\<\\<>! " +
                "</element> " +
                "</line> " +
                "</code>";

        parser.tokenize(testCase);
        Assert.state(parser.getState() == ParserState.SUCCESS, "Parser should end with success");

        List<CourseData> cd = parser.parse();
        Assert.state(parser.getResultState() == ParserResultState.SUCCESS, "Parser result should end with success");
        Assert.notNull(cd,"Parser results should not be null");
    }

    @Test
    void parserJsonCourse(@Autowired CourseDataParser parser) {
        String testCase = "<code><line><element desc=\"json line\">" +
                "<element desc=\"elem name\">\"name\"</element>" +
                ": " +
                "<element desc=\"elem data\">\"data\"</element>" +
                "," +
                "</element>" +
                "</line>" +
                "</code>";

        parser.tokenize(testCase);
        Assert.state(parser.getState() == ParserState.SUCCESS, "Parser should end with success");

        List<CourseData> cd = parser.parse();
        Assert.state(parser.getResultState() == ParserResultState.SUCCESS, "Parser result should end with success");
        Assert.notNull(cd,"Parser results should not be null");
        Assert.notEmpty(cd, "Parser results should not be empty");
    }

    @Test
    void parserJsonCourseNoData(@Autowired CourseDataParser parser) {
        String testCase = "<code><line><element desc=\"json line\">" +
                "<element desc=\"elem name\">\"name\"</element>" +
                "<element desc=\"elem data\">\"data\"</element>" +
                "</element>" +
                "</line>" +
                "</code>";

        parser.tokenize(testCase);
        Assert.state(parser.getState() == ParserState.SUCCESS, "Parser should end with success");

        List<CourseData> cd = parser.parse();
        Assert.state(parser.getResultState() == ParserResultState.ERROR_MISSING_ELEMENT_DATA, "Parser result should end with no data error");
    }

    @Test
    void parserCodeInsideCode(@Autowired CourseDataParser parser) {
        String testCase = "<code> <code> </code> </code>";
        parser.tokenize(testCase);
        Assert.state(parser.getState() == ParserState.SUCCESS, "Parser should end with success");

        List<CourseData> cd = parser.parse();
        Assert.state(parser.getResultState() == ParserResultState.ERROR_UNEXPECTED_TAG, "Code inside code should not be allowed");
        Assert.isNull(cd,"Parser results should be null on error");
    }

    @Test
    void parserLineInsideLine(@Autowired CourseDataParser parser) {
        String testCase = "<code> <line> <line> </line> </line> </code>";
        parser.tokenize(testCase);
        Assert.state(parser.getState() == ParserState.SUCCESS, "Parser should end with success");

        List<CourseData> cd = parser.parse();
        Assert.state(parser.getResultState() == ParserResultState.ERROR_UNEXPECTED_TAG, "Line inside line should not be allowed");
        Assert.isNull(cd,"Parser results should be null on error");
    }

    @Test
    void parserLineWithoutCode(@Autowired CourseDataParser parser) {
        String testCase = "<line> </line>";
        parser.tokenize(testCase);
        Assert.state(parser.getState() == ParserState.SUCCESS, "Parser should end with success");

        List<CourseData> cd = parser.parse();
        Assert.state(parser.getResultState() == ParserResultState.ERROR_UNEXPECTED_TAG, "Line outside code should not be allowed");
        Assert.isNull(cd,"Parser results should be null on error");
    }

    @Test
    void parserElementWithoutLine(@Autowired CourseDataParser parser) {
        String testCase = "<code> <element> </element> </code>";
        parser.tokenize(testCase);
        Assert.state(parser.getState() == ParserState.SUCCESS, "Parser should end with success");

        List<CourseData> cd = parser.parse();
        Assert.state(parser.getResultState() == ParserResultState.ERROR_UNEXPECTED_TAG, "Element outside line should not be allowed");
        Assert.isNull(cd,"Parser results should be null on error");
    }

    @Test
    void parserElementOutsideCode(@Autowired CourseDataParser parser) {
        String testCase = "<element> </element>";
        parser.tokenize(testCase);
        Assert.state(parser.getState() == ParserState.SUCCESS, "Parser should end with success");

        List<CourseData> cd = parser.parse();
        Assert.state(parser.getResultState() == ParserResultState.ERROR_UNEXPECTED_TAG, "Element outside code should not be allowed");
        Assert.isNull(cd,"Parser results should be null on error");
    }

    @Test
    void parserNoCodeEnd(@Autowired CourseDataParser parser) {
        String testCase = "<code>";
        parser.tokenize(testCase);
        Assert.state(parser.getState() == ParserState.SUCCESS, "Parser should end with success");

        List<CourseData> cd = parser.parse();
        Assert.state(parser.getResultState() == ParserResultState.ERROR_MISSING_CODE_END, "Code should have an end");
        Assert.isNull(cd,"Parser results should be null on error");
    }

    @Test
    void parserNoLineEnd(@Autowired CourseDataParser parser) {
        String testCase = "<code> <line> </code>";
        parser.tokenize(testCase);
        Assert.state(parser.getState() == ParserState.SUCCESS, "Parser should end with success");

        List<CourseData> cd = parser.parse();
        Assert.state(parser.getResultState() == ParserResultState.ERROR_MISSING_LINE_END, "Line should have an end");
        Assert.isNull(cd,"Parser results should be null on error");
    }

    @Test
    void parserNoElementEnd(@Autowired CourseDataParser parser) {
        String testCase = "<code> <line> <element> </line> </code>";
        parser.tokenize(testCase);
        Assert.state(parser.getState() == ParserState.SUCCESS, "Parser should end with success");

        List<CourseData> cd = parser.parse();
        Assert.state(parser.getResultState() == ParserResultState.ERROR_MISSING_ELEMENT_END, "Element should have an end");
        Assert.isNull(cd,"Parser results should be null on error");
    }

    @Test
    void parserNoElementEndWithData(@Autowired CourseDataParser parser) {
        String testCase = "<code> <line> <element>ssa </line> </code>";
        parser.tokenize(testCase);
        Assert.state(parser.getState() == ParserState.SUCCESS, "Parser should end with success");

        List<CourseData> cd = parser.parse();
        Assert.state(parser.getResultState() == ParserResultState.ERROR_MISSING_ELEMENT_END, "Element should have an end");
        Assert.isNull(cd,"Parser results should be null on error");
    }

    @Test
    void parserNoElementData(@Autowired CourseDataParser parser) {
        String testCase = "<code> <line> <element> </element> </line> </code>";
        parser.tokenize(testCase);
        Assert.state(parser.getState() == ParserState.SUCCESS, "Parser should end with success");

        List<CourseData> cd = parser.parse();
        System.out.println(parser.getResultState());
        System.out.println(parser.getResultIndexPosition());
        Assert.state(parser.getResultState() == ParserResultState.ERROR_MISSING_ELEMENT_DATA, "Element should have data");
        Assert.isNull(cd,"Parser results should be null on error");
    }

    @Test
    void parserEmptyData(@Autowired CourseDataParser parser) {
        String testCase = "";
        parser.tokenize(testCase);
        Assert.state(parser.getState() == ParserState.SUCCESS, "Parser should end with success");

        List<CourseData> cd = parser.parse();
        Assert.state(parser.getResultState() == ParserResultState.ERROR_NO_DATA, "Data should not be empty");
        Assert.isNull(cd,"Parser results should be null on error");
    }

    @Test
    void parserBlankData(@Autowired CourseDataParser parser) {
        String testCase = "                           ";
        parser.tokenize(testCase);
        Assert.state(parser.getState() == ParserState.SUCCESS, "Parser should end with success");

        List<CourseData> cd = parser.parse();
        Assert.state(parser.getResultState() == ParserResultState.ERROR_NO_DATA, "Data should not be blank");
        Assert.isNull(cd,"Parser results should be null on error");
    }

    @Test
    void parserBlankData255(@Autowired CourseDataParser parser) {
        String testCase = " ";
        parser.tokenize(testCase);
        Assert.state(parser.getState() == ParserState.SUCCESS, "Parser should end with success");

        List<CourseData> cd = parser.parse();
        Assert.state(parser.getResultState() == ParserResultState.ERROR_NO_DATA, "Data should not be blank");
        Assert.isNull(cd,"Parser results should be null on error");
    }

    @Test
    void parserImage(@Autowired CourseDataParser parser) {
        String testCase = "<image id=\"arc\">";

        parser.tokenize(testCase);
        Assert.state(parser.getState() == ParserState.SUCCESS, "Parser should end with success");

        List<CourseData> cd = parser.parse();
        Assert.state(parser.getResultState() == ParserResultState.SUCCESS, "Parser result should end with success");
    }

    @Test
    void parserImageNoId(@Autowired CourseDataParser parser) {
        String testCase = "<image>";

        parser.tokenize(testCase);
        Assert.state(parser.getState() == ParserState.ERROR, "Parser should end with error");
    }

    @Test
    void parserImageInCode(@Autowired CourseDataParser parser) {
        String testCase = "<code><image id=\"arc\"></code>";

        parser.tokenize(testCase);
        Assert.state(parser.getState() == ParserState.SUCCESS, "Parser should end with success");

        List<CourseData> cd = parser.parse();
        Assert.state(parser.getResultState() == ParserResultState.ERROR_UNEXPECTED_TAG, "Parser result should end with error");
    }

    @Test
    void parserImageInLine(@Autowired CourseDataParser parser) {
        String testCase = "<code><line><image id=\"arc\"></line></code>";

        parser.tokenize(testCase);
        Assert.state(parser.getState() == ParserState.SUCCESS, "Parser should end with success");

        List<CourseData> cd = parser.parse();
        Assert.state(parser.getResultState() == ParserResultState.ERROR_UNEXPECTED_TAG, "Parser result should end with error");
    }
    @Test
    void parserImageInLineEnd(@Autowired CourseDataParser parser) {
        String testCase = "<code><line>sometext<image id=\"arc\"></line></code>";

        parser.tokenize(testCase);
        Assert.state(parser.getState() == ParserState.SUCCESS, "Parser should end with success");

        List<CourseData> cd = parser.parse();
        Assert.state(parser.getResultState() == ParserResultState.ERROR_UNEXPECTED_TAG, "Parser result should end with error");
    }

    @Test
    void parserImageBetweenElements(@Autowired CourseDataParser parser) {
        String testCase = "<code><line>sometext<image id=\"arc\">sometext</line></code>";

        parser.tokenize(testCase);
        Assert.state(parser.getState() == ParserState.SUCCESS, "Parser should end with success");

        List<CourseData> cd = parser.parse();
        Assert.state(parser.getResultState() == ParserResultState.ERROR_UNEXPECTED_TAG, "Parser result should end with error");
    }

    @Test
    void parserTokenizerUnknownTagCod(@Autowired CourseDataParser parser) {
        String testCase = "<cod> </code>";
        parser.tokenize(testCase);
        Assert.state(parser.getState() == ParserState.ERROR, "Tag error");
    }

    @Test
    void parserTokenizerUnknownTagLin(@Autowired CourseDataParser parser) {
        String testCase = "<lin> </line>";
        parser.tokenize(testCase);
        Assert.state(parser.getState() == ParserState.ERROR, "Tag error");
    }

    @Test
    void parserTokenizerUnknownTagEl(@Autowired CourseDataParser parser) {
        String testCase = "<el> </element>";
        parser.tokenize(testCase);
        Assert.state(parser.getState() == ParserState.ERROR, "Tag error");
    }

    @Test
    void parserTokenizerUnknownTag(@Autowired CourseDataParser parser) {
        String testCase = "<mm> </mm>";
        parser.tokenize(testCase);
        Assert.state(parser.getState() == ParserState.ERROR, "Tag error");
    }

    @Test
    void parserTokenizerEmpty(@Autowired CourseDataParser parser) {
        String testCase = "";
        parser.tokenize(testCase);
        Assert.state(parser.getState() == ParserState.SUCCESS, "Empty data should be possible");
    }

    @Test
    void parserTokenizerBlank(@Autowired CourseDataParser parser) {
        String testCase = "      ";
        parser.tokenize(testCase);
        Assert.state(parser.getState() == ParserState.SUCCESS, "Blank data should be possible");
    }
}
