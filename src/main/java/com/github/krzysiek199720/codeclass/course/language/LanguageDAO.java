package com.github.krzysiek199720.codeclass.course.language;

import com.github.krzysiek199720.codeclass.core.db.DAO;

import java.util.List;

public interface LanguageDAO extends DAO<Language> {
    public List<Language> getAll();
}
