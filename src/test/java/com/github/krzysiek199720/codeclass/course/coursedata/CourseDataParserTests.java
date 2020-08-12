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
}
