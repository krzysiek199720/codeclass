package com.github.krzysiek199720.codeclass.course.search.dto;

import com.github.krzysiek199720.codeclass.course.course.CourseComplexity;
import com.github.krzysiek199720.codeclass.course.search.response.SearchResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SearchDTO {
    private Long courseGroupId;
    private String courseGroupName;

    private Long courseCount;
    private LocalDateTime lastAddedDate;

    private List<String> complexities;

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

    public SearchResponse toResponse(){
        SearchResponse response = new SearchResponse();
        response.setCourseGroupId(this.courseGroupId);
        response.setCourseGroupName(this.courseGroupName);
        response.setCourseCount(this.courseCount);
        response.setLastAddedDate(this.lastAddedDate);
        response.setLanguageId(this.languageId);
        response.setLanguageName(this.languageName);
        response.setCategoryId(this.categoryId);
        response.setCategoryName(this.categoryName);
        response.setCommentCount(this.commentCount);
        response.setAuthorFirstName(this.authorFirstName);
        response.setAuthorLastName(this.authorLastName);
        response.setAuthorId(this.authorId);

        List<CourseComplexity> complexityList = complexities.stream().map(CourseComplexity::valueOf).collect(Collectors.toList());

        Comparator<CourseComplexity> complexityComparator = (o1, o2) -> {if(o1.ordinal()>o2.ordinal())return 1; else if(o1.ordinal()<o2.ordinal()) return -1; return 0;};

        response.setMinComplexity(complexityList.stream().min(complexityComparator).get());
        response.setMaxComplexity(complexityList.stream().max(complexityComparator).get());

        return response;
    }

}
