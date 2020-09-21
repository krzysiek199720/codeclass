package com.github.krzysiek199720.codeclass.course.course.coursegroup;

import com.github.krzysiek199720.codeclass.auth.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseGroupService {

    private final CourseGroupDAO courseGroupDAO;

    public CourseGroupService(CourseGroupDAO courseGroupDAO) {
        this.courseGroupDAO = courseGroupDAO;
    }

    @Transactional
    public User getUserByCourseGroupId(Long courseGroupId){
        return courseGroupDAO.getUserByCourseGroupId(courseGroupId);
    }

    @Transactional
    public User getUserByCourseId(Long courseId){
        return courseGroupDAO.getUserByCourseId(courseId);
    }
}
