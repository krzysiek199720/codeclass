package com.github.krzysiek199720.codeclass.course.coursegroup;

import com.github.krzysiek199720.codeclass.auth.user.User;
import com.github.krzysiek199720.codeclass.core.db.GenericDAO;
import com.github.krzysiek199720.codeclass.course.course.Course;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Objects;

@Repository
public class CourseGroupDAOImpl extends GenericDAO<CourseGroup> implements CourseGroupDAO {

    @Override
    public CourseGroup getByCourse(Long courseId) {
        Query<CourseGroup> query= getCurrentSession()
                .createQuery("select cg from CourseGroup cg inner join Course c ON c.courseGroup=cg.id where c.id = :courseid", CourseGroup.class)
                .setParameter("courseid", courseId);

        CourseGroup res;
        try{
            res = query.getSingleResult();
        } catch(NoResultException exc){
            return null;
        }

        return res;
    }

    public User getUserByCourseId(Long courseId){
        Query<User> query= getCurrentSession()
                .createQuery("select cg.user from CourseGroup cg inner join Course c ON c.courseGroup=cg.id where c.id = :courseid", User.class)
                .setParameter("courseid", courseId);

        User res;
        try{
            res = query.getSingleResult();
        } catch(NoResultException exc){
            return null;
        }

        return res;
    }

    public User getUserByCourseGroupId(Long courseGroupId){
        Query<User> query= getCurrentSession()
                .createQuery("select cg.user from CourseGroup cg where cg.id = :coursegroupid", User.class)
                .setParameter("coursegroupid", courseGroupId);

        User res;
        try{
            res = query.getSingleResult();
        } catch(NoResultException exc){
            return null;
        }

        return res;
    }

    @Override
    public List<Course> getCourses(Long courseGroupId, boolean showUnpublished) {
        String queryString = "select c from Course c " +
                "inner join c.courseGroup cg " +
                "join fetch c.language " +
                "join fetch c.category " +
                "where cg.id = :courseGroupId ";
        if(!showUnpublished)
            queryString = queryString + "and c.isPublished is not null ";

        queryString = queryString + "order by c.groupOrder asc";

        List<Course> results = getCurrentSession().createQuery(queryString, Course.class)
                .setParameter("courseGroupId", courseGroupId).getResultList();

        return results;
    }


    //----
    @Autowired
    public CourseGroupDAOImpl(EntityManager entityManager){
        super(entityManager);
    }

    @Override
    public CourseGroup getById(Long id) {
        return getCurrentSession().get(CourseGroup.class, id);
    }

    @Override
    public CourseGroup save(CourseGroup object) {
        getCurrentSession().saveOrUpdate(Objects.requireNonNull(object));
        return object;
    }

    @Override
    public void delete(CourseGroup object) {
        getCurrentSession().delete(Objects.requireNonNull(object));
    }
}
