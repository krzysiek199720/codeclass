package com.github.krzysiek199720.codeclass.course.quiz.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class QuizQuestionApi {
    private String question;
    private String answer0;
    private String answer1;
    private String answer2;
    private String answer3;
    private Integer answer;
}
