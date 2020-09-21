package com.github.krzysiek199720.codeclass.course.course.response;

import com.github.krzysiek199720.codeclass.course.course.Course;
import com.github.krzysiek199720.codeclass.course.course.CourseComplexity;
import com.github.krzysiek199720.codeclass.course.category.Category;
import com.github.krzysiek199720.codeclass.course.coursegroup.CourseGroup;
import com.github.krzysiek199720.codeclass.course.language.Language;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CourseResponse {
    private Long id;
    private String title;
    private CourseComplexity complexity;
    private Integer groupOrder;
    private LocalDateTime isPublished;
    private CourseGroup courseGroup;
    private Language language;
    private Category category;
    private Boolean isAuthor;

    public CourseResponse(Course course, boolean isAuthor){

        this.id = course.getId();
        this.title = course.getTitle();
        this.complexity = course.getComplexity();
        this.groupOrder = course.getGroupOrder();
        this.isPublished = course.getIsPublished();
        this.courseGroup = course.getCourseGroup();
        this.language = course.getLanguage();
        this.category = course.getCategory();

        this.isAuthor = isAuthor;
    }
}
