package com.github.krzysiek199720.codeclass.course.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentSaveApi {
    private String data;
    private Long root;
    private Long scriptId;
    private Integer linesFrom;
    private Integer linesTo;
}
