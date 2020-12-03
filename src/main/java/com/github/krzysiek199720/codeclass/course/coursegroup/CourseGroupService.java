package com.github.krzysiek199720.codeclass.course.coursegroup;

import com.github.krzysiek199720.codeclass.auth.user.User;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.NotFoundException;
import com.github.krzysiek199720.codeclass.course.course.Course;
import com.github.krzysiek199720.codeclass.course.course.response.CourseResponseNoGroup;
import com.github.krzysiek199720.codeclass.course.coursegroup.api.CourseGroupSaveApi;
import com.github.krzysiek199720.codeclass.course.coursegroup.response.CourseGroupResponse;
import com.github.krzysiek199720.codeclass.course.follow.Follow;
import com.github.krzysiek199720.codeclass.course.follow.FollowDAO;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseGroupService {

    private final CourseGroupDAO courseGroupDAO;
    private final FollowDAO followDAO;

    @Autowired
    public CourseGroupService(CourseGroupDAO courseGroupDAO, FollowDAO followDAO) {
        this.courseGroupDAO = courseGroupDAO;
        this.followDAO = followDAO;
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
    public CourseGroupResponse getById(Long id, User user) {
        CourseGroup courseGroup = courseGroupDAO.getById(id);
        if(courseGroup == null)
            throw new NotFoundException("course.group.notfound");

        User author = courseGroup.getUser();
        Hibernate.initialize(author);

        Follow follow = null;
        if(user != null)
            follow = followDAO.getByCourseGroupId(courseGroup.getId(), user.getId());

        boolean isFollowing = follow != null;

        return new CourseGroupResponse(courseGroup, author.equals(user), isFollowing);
    }

    @Transactional
    public List<CourseGroupResponse> getByUser(User user) {
        List<CourseGroup> courseGroupList = courseGroupDAO.getAllByAuthor(user.getId());

        return courseGroupList.stream().map(e -> new CourseGroupResponse(e, true, null)).collect(Collectors.toList());
    }

    @Transactional
    public CourseGroupResponse createCourseGroup(CourseGroupSaveApi api, User author){
        CourseGroup courseGroup = new CourseGroup();

        courseGroup.setId(null);
        courseGroup.setName(api.getName());
        courseGroup.setUser(author);

        courseGroupDAO.save(courseGroup);

        return new CourseGroupResponse(courseGroup, true, null);
    }

    @Transactional
    public CourseGroupResponse updateCourseGroup(Long id, CourseGroupSaveApi api){
        CourseGroup courseGroup = courseGroupDAO.getById(id);
        if(courseGroup==null)
            throw new NotFoundException("course.coursegroup.notfound");

        courseGroup.setName(api.getName());

        courseGroupDAO.save(courseGroup);

        User user = courseGroup.getUser();
        Hibernate.initialize(user);

        return new CourseGroupResponse(courseGroup, true, null);
    }

    @Transactional
    public void delete(Long id) {
        CourseGroup courseGroup = courseGroupDAO.getById(id);
        if(courseGroup == null)
            throw new NotFoundException("course.coursegroup.notfound");
        courseGroupDAO.delete(courseGroup);
    }

    @Transactional
    public List<CourseResponseNoGroup> getCourses(Long courseGroupId, User user){
        CourseGroup courseGroup = courseGroupDAO.getById(courseGroupId);
        if(courseGroup == null)
            throw new NotFoundException("course.coursegroup.notfound");

        User author = courseGroupDAO.getUserByCourseGroupId(courseGroup.getId());
        boolean showUnpublished = false;
        if(author.equals(user))
            showUnpublished = true;

        List<Course> result = courseGroupDAO.getCourses(courseGroupId, showUnpublished);

        Follow follow = null;
        if(user != null)
            follow = followDAO.getByCourseGroupId(courseGroup.getId(), user.getId());

        boolean isFollowing = follow != null;

        final boolean isAuthor = showUnpublished;
        return result.stream().map(c -> new CourseResponseNoGroup(c, isAuthor, isFollowing)).collect(Collectors.toList());
    }
}
