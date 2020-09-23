package com.github.krzysiek199720.codeclass.course.coursegroup;

import com.github.krzysiek199720.codeclass.auth.user.User;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.NotFoundException;
import com.github.krzysiek199720.codeclass.course.course.Course;
import com.github.krzysiek199720.codeclass.course.coursegroup.api.CourseGroupSaveApi;
import com.github.krzysiek199720.codeclass.course.coursegroup.response.CourseGroupResponse;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseGroupService {

    private final CourseGroupDAO courseGroupDAO;

    @Autowired
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

    @Transactional
    public CourseGroupResponse getById(Long id, boolean isAuthor) {
        CourseGroup courseGroup = courseGroupDAO.getById(id);
        if(courseGroup == null)
            throw new NotFoundException("course.group.notfound");

        User author = courseGroup.getUser();
        //FIXME Never used it - wonder if it works :>
        Hibernate.initialize(author);

        return new CourseGroupResponse(courseGroup, isAuthor);
    }

    @Transactional
    public CourseGroupResponse createCourseGroup(CourseGroupSaveApi api, User author){
        CourseGroup courseGroup = new CourseGroup();

        courseGroup.setId(null);
        courseGroup.setName(api.getName());
        courseGroup.setUser(author);

        courseGroupDAO.save(courseGroup);

        return new CourseGroupResponse(courseGroup, true);
    }

    @Transactional
    public CourseGroupResponse updateCourseGroup(Long id, CourseGroupSaveApi api){
        CourseGroup courseGroup = new CourseGroup();

        courseGroup.setName(api.getName());

        courseGroupDAO.save(courseGroup);

        User user = courseGroup.getUser();
        //FIXME Never used it - wonder if it works :>
        Hibernate.initialize(user);

        return new CourseGroupResponse(courseGroup, true);
    }

    public void delete(Long id) {
        CourseGroup courseGroup = courseGroupDAO.getById(id);
        if(courseGroup == null)
            throw new NotFoundException("course.coursegroup.notfound");
        courseGroupDAO.delete(courseGroup);
    }
}
