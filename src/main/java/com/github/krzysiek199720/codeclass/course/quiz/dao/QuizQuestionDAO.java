package com.github.krzysiek199720.codeclass.course.quiz.dao;

import com.github.krzysiek199720.codeclass.core.db.DAO;
import com.github.krzysiek199720.codeclass.course.quiz.QuizQuestion;

import java.util.List;

public interface QuizQuestionDAO extends DAO<QuizQuestion> {
    List<QuizQuestion> getAllByQuizId(Long id);
}
