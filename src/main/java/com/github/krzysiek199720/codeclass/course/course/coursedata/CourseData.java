package com.github.krzysiek199720.codeclass.course.course.coursedata;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CourseData {
    private CourseDataType type;
    private Integer order;

    private List<CourseDataLine> courseDataLineList = new LinkedList<>();
}
