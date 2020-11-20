package com.github.krzysiek199720.codeclass.course.quiz.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NotBlank
@Setter
@Getter
public class QuizScoreResponse {
    private Integer points;
    private Integer max;
}
