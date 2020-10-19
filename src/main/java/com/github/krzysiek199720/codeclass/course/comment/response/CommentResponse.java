package com.github.krzysiek199720.codeclass.course.comment.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class CommentResponse {
    private Long id;
    private String data;

    private Long rootId;

    private String userFirstName;
    private String userLastName;
    private String userId;

    private Integer linesFrom;
    private Integer linesTo;
    private List<String> lines;
}
