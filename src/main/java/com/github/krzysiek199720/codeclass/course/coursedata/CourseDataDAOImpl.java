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
                "UPDATE course.coursedata SET courseid = null WHERE courseid = :id")
                .setParameter("id", course.getId())
                .executeUpdate();
    }

    public List<CourseData> getByCourseId(Long courseId){

        List<CourseData> result;

        result = getCurrentSession()
                .createQuery("select cd from CourseData cd " +
                        "WHERE cd.course.id = :courseid", CourseData.class)
                .setParameter("courseid", courseId).getResultList();


        return result;
    }

    @Override
    public List<String> getLines(Long courseDataId, Integer from, Integer to) {
        return (List<String>)getCurrentSession().createNativeQuery("select string_agg(cde.data, '') " +
                " from course.coursedata cd " +
                " inner join course.coursedataline cdl on cdl.coursedataid = cd.id " +
                " inner join course.coursedataelement cde on cde.coursedatalineid = cdl.id " +
                " where cd.id = :cdId and cdl.theorder between :from and :to" +
                " group by cdl.id")
                .setParameter("cdId", courseDataId)
                .setParameter("from", from)
                .setParameter("to", to)
                .getResultList();
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
        for(CourseData cd : objects){
            getCurrentSession().save(cd);
        }
        return objects;
    }

    @Override
    public void delete(CourseData object) {
        getCurrentSession().delete(Objects.requireNonNull(object));
    }

}
