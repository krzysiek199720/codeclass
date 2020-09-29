package com.github.krzysiek199720.codeclass.course.assimilation;

import com.github.krzysiek199720.codeclass.core.db.DAO;

public interface AssimilationDAO extends DAO<Assimilation> {
    Assimilation getByCourseUser(Long courseId, Long userId);
}
