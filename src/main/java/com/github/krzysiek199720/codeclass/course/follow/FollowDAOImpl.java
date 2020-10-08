package com.github.krzysiek199720.codeclass.course.follow;

import com.github.krzysiek199720.codeclass.auth.user.User;
import com.github.krzysiek199720.codeclass.core.db.GenericDAO;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Objects;

@Repository
public class FollowDAOImpl extends GenericDAO<Follow> implements FollowDAO {

    @Override
    public List<Follow> getAllByUser(Long userId) {
        return getCurrentSession().createQuery("select f from Follow f where f.user.id = :userId", Follow.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<User> getAllUsersByCourseGroup(Long courseGroupId) {
        return getCurrentSession().createQuery("select f.user from Follow f where f.courseGroup.id = :courseGroupId", User.class)
                .setParameter("courseGroupId", courseGroupId)
                .getResultList();
    }

    @Override
    public Follow getByCourseId(Long courseId, Long userId) {
        Query<Follow> query = getCurrentSession()
                .createQuery("select f from Course c, CourseGroup cg, Follow f where c.id = :courseId and f.user.id = :userId",
                        Follow.class)
                .setParameter("courseId", courseId)
                .setParameter("userId", userId);
        Follow follow;
        try {
            follow = query.getSingleResult();
        }
        catch (NoResultException exc){
            return null;
        }
        return follow;
    }

    @Override
    public Follow getByCourseGroupId(Long courseGroupId, Long userId) {
        Query<Follow> query = getCurrentSession()
                .createQuery("select f from Follow f where f.courseGroup.id = :courseGroupId and f.user.id = :userId",
                        Follow.class)
                .setParameter("courseGroupId", courseGroupId)
                .setParameter("userId", userId);
        Follow follow;
        try {
            follow = query.getSingleResult();
        }
        catch (NoResultException exc){
            return null;
        }
        return follow;
    }

    //----
    @Autowired
    public FollowDAOImpl(EntityManager entityManager){
        super(entityManager);
    }

    @Override
    public Follow getById(Long id) {
        return getCurrentSession().get(Follow.class, id);
    }

    @Override
    public Follow save(Follow object) {
        getCurrentSession().saveOrUpdate(Objects.requireNonNull(object));
        return object;
    }

    @Override
    public void delete(Follow object) {
        getCurrentSession().delete(Objects.requireNonNull(object));
    }
}
