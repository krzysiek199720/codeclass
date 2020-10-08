package com.github.krzysiek199720.codeclass.course.quiz.dao;

import com.github.krzysiek199720.codeclass.core.db.GenericDAO;
import com.github.krzysiek199720.codeclass.course.quiz.Quiz;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Objects;

@Repository
public class QuizDAOImpl extends GenericDAO<Quiz> implements QuizDAO {

    @Override
    public Quiz getByCourseId(Long courseId) {
        Query<Quiz> query = getCurrentSession().createQuery("select q from Quiz q where q.course.id = :courseId", Quiz.class);
        query.setParameter("courseId", courseId);

        Quiz quiz;
        try{
            quiz = query.getSingleResult();
        } catch(NoResultException exc){
            return null;
        }

        return quiz;
    }

    //----
    @Autowired
    public QuizDAOImpl(EntityManager entityManager){
        super(entityManager);
    }

    @Override
    public Quiz getById(Long id) {
        return getCurrentSession().get(Quiz.class, id);
    }

    @Override
    public Quiz save(Quiz object) {
        getCurrentSession().saveOrUpdate(Objects.requireNonNull(object));
        return object;
    }

    @Override
    public void delete(Quiz object) {
        getCurrentSession().delete(Objects.requireNonNull(object));
    }

}
