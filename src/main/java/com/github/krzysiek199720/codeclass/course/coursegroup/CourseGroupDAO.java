package com.github.krzysiek199720.codeclass.course.coursegroup;

import com.github.krzysiek199720.codeclass.auth.user.User;
import com.github.krzysiek199720.codeclass.core.db.DAO;
import com.github.krzysiek199720.codeclass.course.course.Course;

import java.util.List;

public interface CourseGroupDAO extends DAO<CourseGroup> {
    public User getUserByCourseId(Long courseId);
    public User getUserByCourseGroupId(Long courseGroupId);
    public List<Course> getCourses(Long courseGroupId, boolean showUnpublished);
}
