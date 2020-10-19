package com.github.krzysiek199720.codeclass.course.comment.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class CommentScriptMentionResponse {
    private Integer linesFrom;
    private Integer linesTo;

    private String data;
}
