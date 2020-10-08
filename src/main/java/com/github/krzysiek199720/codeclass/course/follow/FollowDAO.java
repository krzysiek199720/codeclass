package com.github.krzysiek199720.codeclass.course.follow;

import com.github.krzysiek199720.codeclass.auth.user.User;
import com.github.krzysiek199720.codeclass.core.db.DAO;

import java.util.List;

public interface FollowDAO extends DAO<Follow> {
    List<Follow> getAllByUser(Long userId);
    List<User> getAllUsersByCourseGroup(Long userId);

    Follow getByCourseId(Long courseId, Long userId);
    Follow getByCourseGroupId(Long courseId, Long userId);
}
