package com.github.krzysiek199720.codeclass.course.course.coursegroup;

import com.github.krzysiek199720.codeclass.auth.user.User;
import com.github.krzysiek199720.codeclass.core.db.DAO;
import com.github.krzysiek199720.codeclass.course.course.Course;

public interface CourseGroupDAO extends DAO<CourseGroup> {
    public User getUserByCourseId(Long courseId);
}