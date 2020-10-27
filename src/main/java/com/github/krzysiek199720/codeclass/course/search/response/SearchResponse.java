package com.github.krzysiek199720.codeclass.course.search.response;

import com.github.krzysiek199720.codeclass.course.course.CourseComplexity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SearchResponse {
    private Long courseGroupId;
    private String courseGroupName;

    private Long courseCount;
    private LocalDateTime lastAddedDate;

    private CourseComplexity minComplexity;
    private CourseComplexity maxComplexity;

//    most occurrences
    private Long languageId;
    private String languageName;

//    most occurrences
    private Long categoryId;
    private String categoryName;

    private Long commentCount;

    private String authorFirstName;
    private String authorLastName;
    private Long authorId;

}
