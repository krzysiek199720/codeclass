package com.github.krzysiek199720.codeclass.course.coursedata;

import com.github.krzysiek199720.codeclass.course.course.coursedata.parser.CourseDataParser;
import com.github.krzysiek199720.codeclass.course.course.coursedata.parser.ParserState;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
public class CourseDataParserTests {

    @Test
    void parserWorksAllElements(@Autowired CourseDataParser parser) {
        String testCase = "Here is where the text could be inserted.\n" +
                          "<code>\n" +
                          "    <line indent=\"1\">\n" +
                          "        Here is where magic happens.\n" +
                          "        <element desc=\"Description of magic\">\n" +
                          "            Magic is awesome!\n" +
                          "        </element>\n" +
                          "    </line>\n" +
                          "    <line>\n" +
                          "        <element desc=\"Description one\">\n" +
                          "            One.text\n" +
                          "            <element desc=\"Description one.one\">\n" +
                          "                One.one.text\n" +
                          "            </element>\n" +
                          "        </element>\n" +
                          "    </line>\n" +
                          "</code>\n" +
                          "Just want to say thank you.\n";

        parser.parse(testCase);
        Assert.state(parser.getState() != ParserState.ERROR, "Parser should end with success");
    }


    @Test
    void parserWorksAllElementsMinified(@Autowired CourseDataParser parser) {
        String testCase = "Here is where the text could be inserted.<code><line indent=\"1\">Here is where magic happens.<element desc=\"Description of magic\">Magic is awesome!</element></line><line><element desc=\"Description one\">One.text<element desc=\"Description one.one\">One.one.text</element></element></line></code>Just want to say thank you.";

        parser.parse(testCase);
        Assert.state(parser.getState() != ParserState.ERROR, "Parser should end with success");
    }
}
