package com.github.krzysiek199720.codeclass.course.course.coursedata;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseDataElement {
    private Integer order;
    private Integer depth;
    private String data;
    private String description;
    private CourseDataLine courseDataLine;
}
