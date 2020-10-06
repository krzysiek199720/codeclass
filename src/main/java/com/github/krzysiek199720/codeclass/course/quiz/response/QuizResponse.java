package com.github.krzysiek199720.codeclass.course.quiz.response;

import com.github.krzysiek199720.codeclass.course.quiz.QuizQuestion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class QuizResponse {
    private Integer maxScore;
    private List<QuizQuestion> questions;
}
