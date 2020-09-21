package com.github.krzysiek199720.codeclass.course.coursedata;

import com.github.krzysiek199720.codeclass.core.db.GenericDAO;
import com.github.krzysiek199720.codeclass.course.course.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
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

        result = getCurrentSession()
                .createQuery("select cd from CourseDataElement cde " +
                        "inner join fetch cde.courseDataLine cdl " +
                        "inner join fetch cdl.courseData cd " +
                        "WHERE cd.course = :courseid", CourseData.class)
                .setParameter("courseid", courseId).getResultList();

        return result;
    }


//----
    @Autowired
    public CourseDataDAOImpl(EntityManager entityManager){
        super(entityManager);
    }

    @Override
    public CourseData getById(Long id) {

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
