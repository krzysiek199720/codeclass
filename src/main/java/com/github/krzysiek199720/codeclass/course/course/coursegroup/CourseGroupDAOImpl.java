package com.github.krzysiek199720.codeclass.course.course.coursegroup;

import com.github.krzysiek199720.codeclass.auth.role.Role;
import com.github.krzysiek199720.codeclass.auth.user.User;
import com.github.krzysiek199720.codeclass.core.db.GenericDAO;
import com.github.krzysiek199720.codeclass.course.course.Course;
import com.github.krzysiek199720.codeclass.course.course.CourseDAO;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Objects;

@Repository
public class CourseGroupDAOImpl extends GenericDAO<CourseGroup> implements CourseGroupDAO {

    public User getUserByCourseId(Long courseId){
        Query<User> query= getCurrentSession()
                .createQuery("select cg.user from Course c join c.courseGroup cg where c.id = :courseid", User.class)
                .setParameter("courseid", courseId);

        User res;
        try{
            res = query.getSingleResult();
        } catch(NoResultException exc){
            return null;
        }

        return res;
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
