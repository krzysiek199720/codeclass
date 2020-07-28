package com.github.krzysiek199720.codeclass.core.db;

import org.hibernate.Session;

import javax.persistence.EntityManager;

public abstract class GenericDAO<T> implements DAO<T>{
    protected final EntityManager entityManager;

    public GenericDAO(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    protected Session getCurrentSession(){
        return entityManager.unwrap(Session.class);
    }
}
