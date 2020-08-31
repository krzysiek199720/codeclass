package com.github.krzysiek199720.codeclass.core.db;

import java.util.Collection;

public interface DAO<T> {

    T save(T object);
    void delete(T object);

    T getById(Long id);
}
