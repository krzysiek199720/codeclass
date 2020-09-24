package com.github.krzysiek199720.codeclass.course.course;

import com.github.krzysiek199720.codeclass.core.db.GenericDAO;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Objects;

@Repository
public class CourseDAOImpl extends GenericDAO<Course> implements CourseDAO {

    public Course fetchById(Long id) {
//        Wht tho. Why fetch all properties doesnt work
        Query<Course> query = getCurrentSession().createQuery("select c from Course c " +
                        "join fetch c.courseGroup " +
                        "join fetch c.language " +
                        "join fetch c.category " +
                        " where c.id = :courseId"
                , Course.class)
                .setParameter("courseId", id);
        Course res;
        try{
            res = query.getSingleResult();
        } catch(NoResultException exc){
            return null;
        }
        return res;
    }
//----
    @Autowired
    public CourseDAOImpl(EntityManager entityManager){
        super(entityManager);
    }

    @Override
    public Course getById(Long id) {
        return getCurrentSession().get(Course.class, id);
    }

    @Override
    public Course save(Course object) {
        getCurrentSession().saveOrUpdate(Objects.requireNonNull(object));
        return object;
    }

    @Override
    public void delete(Course object) {
        getCurrentSession().delete(Objects.requireNonNull(object));
    }
}
