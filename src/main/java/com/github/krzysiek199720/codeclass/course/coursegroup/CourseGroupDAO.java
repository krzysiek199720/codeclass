package com.github.krzysiek199720.codeclass.course.coursegroup;

import com.github.krzysiek199720.codeclass.auth.user.User;
import com.github.krzysiek199720.codeclass.core.db.DAO;

public interface CourseGroupDAO extends DAO<CourseGroup> {
    public User getUserByCourseId(Long courseId);
    public User getUserByCourseGroupId(Long courseGroupId);
}
