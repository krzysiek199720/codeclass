package com.github.krzysiek199720.codeclass.course.course.coursedata;

import com.github.krzysiek199720.codeclass.core.db.DAO;

import java.util.List;

public interface CourseDataDAO extends DAO<CourseData> {

    public List<CourseData> saveAll(List<CourseData> objects);

}
