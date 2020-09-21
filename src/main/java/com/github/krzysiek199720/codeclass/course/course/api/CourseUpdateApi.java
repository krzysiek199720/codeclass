package com.github.krzysiek199720.codeclass.course.course.api;

import com.github.krzysiek199720.codeclass.course.course.CourseComplexity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CourseUpdateApi {
        private String title;
        private CourseComplexity complexity;
        private Integer groupOrder;
        private Long languageId;
        private Long categoryId;
    }