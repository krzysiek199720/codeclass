package com.github.krzysiek199720.codeclass.course.course.coursedata.parser;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class Index {
    int position;
    int length;
    ElementType type = null;
}
