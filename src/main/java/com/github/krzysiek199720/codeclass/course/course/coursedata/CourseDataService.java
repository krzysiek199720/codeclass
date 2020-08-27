package com.github.krzysiek199720.codeclass.course.course.coursedata;

import com.github.krzysiek199720.codeclass.course.course.coursedata.parser.CourseDataParser;
import com.github.krzysiek199720.codeclass.course.course.coursedata.parser.ParserResultState;
import com.github.krzysiek199720.codeclass.course.course.coursedata.parser.ParserState;
import com.github.krzysiek199720.codeclass.course.course.coursedata.parser.exception.exception.CourseDataParserParseErrorException;
import com.github.krzysiek199720.codeclass.course.course.coursedata.parser.exception.exception.CourseDataParserTokenizerErrorException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class CourseDataService {

    private final ObjectFactory<CourseDataParser> factory;

    @Autowired
    public CourseDataService(ObjectFactory<CourseDataParser> factory){
        this.factory = factory;
    }

    public List<CourseData> parseCourseData(String input){
        CourseDataParser parser = factory.getObject();

        parser.tokenize(input);

        if(parser.getState() == ParserState.ERROR){
            throw new CourseDataParserTokenizerErrorException(parser.getState(), parser.getDataPosition());
        }

        List<CourseData> result = parser.parse();

        if(parser.getResultState() != ParserResultState.SUCCESS || result == null){
            throw new CourseDataParserParseErrorException(
                    parser.getResultState(),
                    parser.getResultIndexPosition());
        }

        return result;
    }

    @Transactional
    public List<CourseData> saveCourseData(Long courseId, String input){
        List<CourseData> result = parseCourseData(input);

        //TODO delete old one, delete old source file path from db, then save


        return result;
    }

    @Transactional
    public List<CourseData> getCourseData(Long courseId){
        //TODO

        return null;
    }


}
