package com.github.krzysiek199720.codeclass.course.course.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PublishApi {
    private Boolean isPublished;
    private String slug;
}
