package com.github.krzysiek199720.codeclass.course.course.coursedata;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class CourseDataLine {
    private Integer order;
    private Integer indent;
    private CourseData courseData;

    private List<CourseDataElement> courseDataElementList;
}
