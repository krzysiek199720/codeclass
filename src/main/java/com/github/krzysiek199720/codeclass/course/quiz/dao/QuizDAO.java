package com.github.krzysiek199720.codeclass.course.quiz.dao;

import com.github.krzysiek199720.codeclass.core.db.DAO;
import com.github.krzysiek199720.codeclass.course.quiz.Quiz;

public interface QuizDAO extends DAO<Quiz> {
    Quiz getByCourseId(Long courseId);
}
