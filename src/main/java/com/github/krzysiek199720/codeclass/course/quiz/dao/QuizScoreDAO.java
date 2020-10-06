package com.github.krzysiek199720.codeclass.course.quiz.dao;

import com.github.krzysiek199720.codeclass.core.db.DAO;
import com.github.krzysiek199720.codeclass.course.quiz.QuizScore;

public interface QuizScoreDAO extends DAO<QuizScore> {
    QuizScore getByQuizAndUserId(Long quizId, Long userId);

    Integer getByCourseAndUserId(Long courseId, Long id);
}
