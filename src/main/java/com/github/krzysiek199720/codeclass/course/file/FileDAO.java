package com.github.krzysiek199720.codeclass.course.file;

import com.github.krzysiek199720.codeclass.auth.user.User;
import com.github.krzysiek199720.codeclass.core.db.DAO;

import java.util.List;

public interface FileDAO extends DAO<File> {
    List<File> getAddByCourse(Long courseId);

    User getUserByFile(Long id);
}
