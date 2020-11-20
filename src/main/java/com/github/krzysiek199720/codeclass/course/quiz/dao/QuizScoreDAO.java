package com.github.krzysiek199720.codeclass.course.quiz.dao;

import com.github.krzysiek199720.codeclass.core.db.DAO;
import com.github.krzysiek199720.codeclass.course.quiz.QuizScore;
import com.github.krzysiek199720.codeclass.course.quiz.response.QuizScoreResponse;

public interface QuizScoreDAO extends DAO<QuizScore> {
    QuizScore getByQuizAndUserId(Long quizId, Long userId);

    QuizScoreResponse getByCourseAndUserId(Long courseId, Long id);
}
