package com.github.krzysiek199720.codeclass.course.quiz.api;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class QuizSaveApi {
    private Integer maxScore;
    private List<QuizQuestionApi> questionApiList;
}
