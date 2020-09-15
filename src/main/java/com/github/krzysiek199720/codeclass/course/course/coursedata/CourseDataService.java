package com.github.krzysiek199720.codeclass.course.course.coursedata;

import com.github.krzysiek199720.codeclass.course.course.Course;
import com.github.krzysiek199720.codeclass.course.course.CourseDAO;
import com.github.krzysiek199720.codeclass.course.course.coursedata.parser.CourseDataParser;
import com.github.krzysiek199720.codeclass.course.course.coursedata.parser.ParserResultState;
import com.github.krzysiek199720.codeclass.course.course.coursedata.parser.ParserState;
import com.github.krzysiek199720.codeclass.course.course.coursedata.parser.exception.exception.CourseDataParserParseErrorException;
import com.github.krzysiek199720.codeclass.course.course.coursedata.parser.exception.exception.CourseDataParserTokenizerErrorException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Service
public class CourseDataService {

    private final ObjectFactory<CourseDataParser> factory;

    private final CourseDataDAO courseDataDAO;
    private final CourseDAO courseDAO;

    @Value("${codeclass.course.sourcedir:./course/sources}")
    private String sourceDirectory;

    @Autowired
    public CourseDataService(ObjectFactory<CourseDataParser> factory, CourseDataDAO courseDataDAO, CourseDAO courseDAO){
        this.factory = factory;
        this.courseDataDAO = courseDataDAO;
        this.courseDAO = courseDAO;
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

        if(result.size() < 1){
            throw new CourseDataParserParseErrorException(
                    ParserResultState.ERROR_NO_DATA,
                    0);
        }

        return result;
    }

    @Transactional
    public List<CourseData> saveCourseData(Long courseId, String input){
        List<CourseData> result = parseCourseData(input);

        Course course = courseDAO.getById(courseId);

        result.forEach(e -> e.setCourse(course));

        if(course.getSourcePath().isBlank()){
            course.setSourcePath(String.format("%019d", courseId) + ".course");
        }

        File sourceFile = new File(sourceDirectory, course.getSourcePath());
        FileWriter fw = null;
        try{
            sourceFile.createNewFile(); // does not create if file exists
            fw = new FileWriter(sourceFile);
            fw.write(input);
            fw.close();
        }catch (IOException e){
            throw new RuntimeException("Could not save course source file");
        }
        courseDAO.save(course);

        courseDataDAO.deleteOld(course);

        result = courseDataDAO.saveAll(result);

        return result;
    }

    @Transactional
    public List<CourseData> getCourseData(Long courseId){

        return courseDataDAO.getByCourseId(courseId);
    }


}
