package com.github.krzysiek199720.codeclass.course.course;

import com.github.krzysiek199720.codeclass.core.db.DAO;

public interface CourseDAO extends DAO<Course> {
    Course fetchById(Long id);
}
