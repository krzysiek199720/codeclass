package com.github.krzysiek199720.codeclass.course.coursedata;

import com.github.krzysiek199720.codeclass.core.db.DAO;
import com.github.krzysiek199720.codeclass.course.course.Course;

import java.util.List;

public interface DataImageDAO extends DAO<DataImage> {

    DataImage get(Long courseId, String localId);
    List<DataImage> getAll(Long courseId);
}
