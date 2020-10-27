package com.github.krzysiek199720.codeclass.course.coursegroup;

import com.github.krzysiek199720.codeclass.auth.user.User;
import com.github.krzysiek199720.codeclass.core.db.DAO;
import com.github.krzysiek199720.codeclass.course.course.Course;
import com.github.krzysiek199720.codeclass.course.course.CourseComplexity;
import com.github.krzysiek199720.codeclass.course.search.dto.SearchDTO;

import java.util.List;

public interface CourseGroupDAO extends DAO<CourseGroup> {
    public User getUserByCourseId(Long courseId);
    public User getUserByCourseGroupId(Long courseGroupId);
    public List<Course> getCourses(Long courseGroupId, boolean showUnpublished);
    CourseGroup getByCourse(Long courseId);

    List<SearchDTO> search(String searchQuery, List<CourseComplexity> complexities, Long userId);
}
