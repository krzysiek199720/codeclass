package com.github.krzysiek199720.codeclass.course.course;

import com.github.krzysiek199720.codeclass.core.db.GenericDAO;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.Objects;

public class CourseDAOImpl extends GenericDAO<Course> implements CourseDAO {


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
