package com.github.krzysiek199720.codeclass.course.course.response;

import com.github.krzysiek199720.codeclass.course.course.Course;
import com.github.krzysiek199720.codeclass.course.course.CourseComplexity;
import com.github.krzysiek199720.codeclass.course.course.category.Category;
import com.github.krzysiek199720.codeclass.course.course.coursegroup.CourseGroup;
import com.github.krzysiek199720.codeclass.course.course.language.Language;

import java.time.LocalDateTime;

public class CourseResponse {
    private Long id;
    private String title;
    private CourseComplexity complexity;
    private Integer groupOrder;
    private LocalDateTime isPublished;
    private CourseGroup courseGroupId;
    private Language languageId;
    private Category categoryId;
    private Boolean isAuthor;

    public CourseResponse(Course course, boolean isAuthor){

        this.id = course.getId();
        this.title = course.getTitle();
        this.complexity = course.getComplexity();
        this.groupOrder = course.getGroupOrder();
        this.isPublished = course.getIsPublished();
        this.courseGroupId = course.getCourseGroup().;
        this.languageId = course.getLanguage();
        this.categoryId = course.getCategory();

        this.isAuthor = isAuthor;
    }
}
