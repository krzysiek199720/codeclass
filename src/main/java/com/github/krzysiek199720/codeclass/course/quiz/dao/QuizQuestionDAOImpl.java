package com.github.krzysiek199720.codeclass.course.quiz.dao;

import com.github.krzysiek199720.codeclass.core.db.GenericDAO;
import com.github.krzysiek199720.codeclass.course.quiz.QuizQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;

@Repository
public class QuizQuestionDAOImpl extends GenericDAO<QuizQuestion> implements QuizQuestionDAO {

    @Override
    public List<QuizQuestion> getAllByQuizId(Long id) {
        return getCurrentSession().createQuery("select qq from QuizQuestion qq where qq.quiz.id = :quizId", QuizQuestion.class)
                .setParameter("quizId", id).getResultList();
    }

    //----
    @Autowired
    public QuizQuestionDAOImpl(EntityManager entityManager){
        super(entityManager);
    }

    @Override
    public QuizQuestion getById(Long id) {
        return getCurrentSession().get(QuizQuestion.class, id);
    }

    @Override
    public QuizQuestion save(QuizQuestion object) {
        getCurrentSession().saveOrUpdate(Objects.requireNonNull(object));
        return object;
    }

    @Override
    public void delete(QuizQuestion object) {
        getCurrentSession().delete(Objects.requireNonNull(object));
    }
}
