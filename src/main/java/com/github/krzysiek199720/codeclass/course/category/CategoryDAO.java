package com.github.krzysiek199720.codeclass.course.category;

import com.github.krzysiek199720.codeclass.core.db.DAO;

import java.util.List;

public interface CategoryDAO extends DAO<Category> {
    List<Category> getAll();
}
