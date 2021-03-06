package com.github.krzysiek199720.codeclass.course.course.response;

import com.github.krzysiek199720.codeclass.course.category.Category;
import com.github.krzysiek199720.codeclass.course.course.Course;
import com.github.krzysiek199720.codeclass.course.course.CourseComplexity;
import com.github.krzysiek199720.codeclass.course.language.Language;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CourseResponseNoGroup {
    private Long id;
    private String title;
    private CourseComplexity complexity;
    private Integer groupOrder;
    private LocalDateTime isPublished;
    private Language language;
    private Category category;
    private Boolean isAuthor;
    private Boolean isFollowing;

    public CourseResponseNoGroup(Course course, boolean isAuthor, boolean isFollowing){

        this.id = course.getId();
        this.title = course.getTitle();
        this.complexity = course.getComplexity();
        this.groupOrder = course.getGroupOrder();
        this.isPublished = course.getIsPublished();
        this.language = course.getLanguage();
        this.category = course.getCategory();

        this.isAuthor = isAuthor;
        this.isFollowing = isFollowing;
    }
}
