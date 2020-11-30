package com.github.krzysiek199720.codeclass.course.coursedata.response;

import com.github.krzysiek199720.codeclass.course.coursedata.CourseDataLine;
import com.github.krzysiek199720.codeclass.course.coursedata.CourseDataType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CourseDataResponse {
    protected Long id;
    private CourseDataType type;
    private Integer order;
    private List<CourseDataLine> courseDataLineList;
    private List<String> linesPlain;
}
