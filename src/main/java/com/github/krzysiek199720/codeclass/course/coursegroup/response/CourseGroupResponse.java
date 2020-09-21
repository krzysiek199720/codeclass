package com.github.krzysiek199720.codeclass.course.coursegroup.response;

import com.github.krzysiek199720.codeclass.course.coursegroup.CourseGroup;

public class CourseGroupResponse {
    private Long id;
    private String name;

    private String authorFirstname;
    private String authorLastname;
    private String authorEmail;

    private Boolean isAuthor;

    public CourseGroupResponse(CourseGroup cg, Boolean isAuthor){
        this.id = cg.getId();
        this.name = cg.getName();
        this.authorFirstname = cg.getUser().getFirstname();
        this.authorLastname = cg.getUser().getLastname();
        this.authorEmail = cg.getUser().getEmail();

        this.isAuthor = isAuthor;
    }
}
