package com.github.krzysiek199720.codeclass.course.quiz.dao;

import com.github.krzysiek199720.codeclass.core.db.GenericDAO;
import com.github.krzysiek199720.codeclass.course.quiz.QuizScore;
import com.github.krzysiek199720.codeclass.course.quiz.response.QuizScoreResponse;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Objects;

@Repository
public class QuizScoreDAOImpl extends GenericDAO<QuizScore> implements QuizScoreDAO {

    @Override
    public QuizScore getByQuizAndUserId(Long quizId, Long userId) {
        Query<QuizScore> query = getCurrentSession().createQuery("select qs from QuizScore qs where qs.quiz.id = :quizId and qs.user.id = :userId", QuizScore.class)
                .setParameter("quizId", quizId)
                .setParameter("userId", userId);

        QuizScore qs;
        try{
            qs = query.getSingleResult();
        } catch(NoResultException exc){
            return null;
        }

        return qs;
    }

    @Override
    public QuizScoreResponse getByCourseAndUserId(Long courseId, Long userId) {
        Query<QuizScoreResponse> query = getCurrentSession()
                .createQuery("select new com.github.krzysiek199720.codeclass.course.quiz.response.QuizScoreResponse(qs.score, q.maxScore) " +
                        "from QuizScore qs, Quiz q where q.course.id = :courseId and qs.user.id = :userId", QuizScoreResponse.class)
                .setParameter("courseId", courseId)
                .setParameter("userId", userId);

        QuizScoreResponse qs;
        try{
            qs = query.getSingleResult();
        } catch(NoResultException exc){
            return null;
        }

        return qs;
    }

    //----
    @Autowired
    public QuizScoreDAOImpl(EntityManager entityManager){
        super(entityManager);
    }

    @Override
    public QuizScore getById(Long id) {
        return getCurrentSession().get(QuizScore.class, id);
    }

    @Override
    public QuizScore save(QuizScore object) {
        getCurrentSession().saveOrUpdate(Objects.requireNonNull(object));
        return object;
    }

    @Override
    public void delete(QuizScore object) {
        getCurrentSession().delete(Objects.requireNonNull(object));
    }

}
