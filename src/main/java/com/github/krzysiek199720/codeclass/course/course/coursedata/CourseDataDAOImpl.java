package com.github.krzysiek199720.codeclass.course.course.coursedata;

import com.github.krzysiek199720.codeclass.core.db.GenericDAO;
import com.github.krzysiek199720.codeclass.course.course.Course;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CourseDataDAOImpl extends GenericDAO<CourseData> implements CourseDataDAO{

    @Override
    public void deleteOld(Course course) {
        getCurrentSession().createNativeQuery(
                "DELETE FROM course.coursedata WHERE courseid = ?")
                .setParameter(0, course.getId())
                .executeUpdate();
    }

    public List<CourseData> getByCourseId(Long courseId){
//        fixme need a db func for that, or just use hibernate - not sure how fast it is

        List<CourseData> result;

        result = getCurrentSession().createQuery("FROM CourseData WHERE course = ?", CourseData.class)
                .setParameter(0, courseId).getResultList();

        return result;
    }


//----
    @Autowired
    public CourseDataDAOImpl(EntityManager entityManager){
        super(entityManager);
    }

    @Override
    public CourseData getById(Long id) {
//        fixme need a db func for that, or just use hibernate - not sure how fast it is

        return getCurrentSession().get(CourseData.class, id);
    }

    @Override
    public CourseData save(CourseData object) {
        getCurrentSession().saveOrUpdate(Objects.requireNonNull(object));
        return object;
    }

    public List<CourseData> saveAll(List<CourseData> objects){
//        fixme look into batch saving
        List<CourseData> result = new ArrayList<>(objects.size());

        for(CourseData cd : objects){
            getCurrentSession().save(cd);
            result.add(cd);
        }
        return result;
    }

    @Override
    public void delete(CourseData object) {
        getCurrentSession().delete(Objects.requireNonNull(object));
    }

}
