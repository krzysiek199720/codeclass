package com.github.krzysiek199720.codeclass.course.link;

import com.github.krzysiek199720.codeclass.auth.user.User;
import com.github.krzysiek199720.codeclass.core.db.DAO;

import java.util.List;

public interface LinkDAO extends DAO<Link> {
    List<Link> getAllByCourse(Long courseId);
    User getUserByLinkId(Long id);
}
