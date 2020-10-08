package com.github.krzysiek199720.codeclass.course.follow;

import com.github.krzysiek199720.codeclass.auth.user.User;
import com.github.krzysiek199720.codeclass.core.exceptions.exception.NotFoundException;
import com.github.krzysiek199720.codeclass.course.coursegroup.CourseGroup;
import com.github.krzysiek199720.codeclass.course.coursegroup.CourseGroupDAO;
import com.github.krzysiek199720.codeclass.course.follow.response.FollowResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class FollowService {

    private final FollowDAO followDAO;
    private final CourseGroupDAO courseGroupDAO;

    @Autowired
    public FollowService(FollowDAO followDAO, CourseGroupDAO courseGroupDAO) {
        this.followDAO = followDAO;
        this.courseGroupDAO = courseGroupDAO;
    }


    public List<FollowResponse> getAll(User user) {
        return null;
    }

    @Transactional
    public void saveFollowByCourse(Long courseId, Boolean doFollow, User user) {
        CourseGroup courseGroup = courseGroupDAO.getByCourse(courseId);
        if(courseGroup == null)
            throw new NotFoundException("course.group.notfound");

        Follow follow = followDAO.getByCourseId(courseId, user.getId());
        this.save(follow, doFollow, user, courseGroup);
    }

    @Transactional
    public void saveFollowByCourseGroup(Long courseGroupId, Boolean doFollow, User user) {
        CourseGroup courseGroup = courseGroupDAO.getById(courseGroupId);
        if(courseGroup == null)
            throw new NotFoundException("course.group.notfound");

        Follow follow = followDAO.getByCourseGroupId(courseGroupId, user.getId());
        this.save(follow, doFollow, user, courseGroup);
    }

    private void save(Follow follow, Boolean doFollow, User user, CourseGroup courseGroup){

        if(doFollow){
            if(follow == null){
                follow = new Follow();
                follow.setId(null);
                follow.setCourseGroup(courseGroup);
                follow.setUser(user);
                followDAO.save(follow);
            }

        }else{
            if(follow != null){
                followDAO.delete(follow);
            }
        }
    }
}
