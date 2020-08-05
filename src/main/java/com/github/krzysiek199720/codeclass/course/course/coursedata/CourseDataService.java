package com.github.krzysiek199720.codeclass.course.course.coursedata;

import com.github.krzysiek199720.codeclass.course.course.coursedata.parser.CourseDataParser;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class CourseDataService {

    @Autowired
    private ObjectFactory<CourseDataParser> factory;

}
