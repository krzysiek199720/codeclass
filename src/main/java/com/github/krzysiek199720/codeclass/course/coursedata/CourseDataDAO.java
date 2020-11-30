package com.github.krzysiek199720.codeclass.course.coursedata;

import com.github.krzysiek199720.codeclass.core.db.DAO;
import com.github.krzysiek199720.codeclass.course.course.Course;

import java.util.List;

public interface CourseDataDAO extends DAO<CourseData> {

    public List<CourseData> saveAll(List<CourseData> objects);

    public void deleteOld(Course course);

    public List<CourseData> getByCourseId(Long courseId);

    List<String> getLines(Long courseDataId, Integer from, Integer to);
    List<String> getLines(Long courseDataId);

}
