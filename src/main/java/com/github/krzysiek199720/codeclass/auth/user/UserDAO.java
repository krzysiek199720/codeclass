package com.github.krzysiek199720.codeclass.auth.user;

import com.github.krzysiek199720.codeclass.core.db.GenericDAO;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Repository
public class UserDAO extends GenericDAO<User> {

    public User findByEmail(String email){
        Query<User> query = getCurrentSession().createQuery("from User where email LIKE :email", User.class);
        query.setParameter("email", email);

        User user;
        try{
            user = query.getSingleResult();
        } catch(NoResultException exc){
            return null;
        }

        return user;
    }

    public boolean isEmailTaken(String email){
        Query<Long> query = getCurrentSession().createQuery("select count(*) from User where email LIKE :email", Long.class)
                .setParameter("email", email);

        return query.getSingleResult() > 0;
    }

//----
    @Autowired
    public UserDAO(EntityManager entityManager){
        super(entityManager);
    }

    @Override
    public User getById(Long id) {
        return getCurrentSession().get(User.class, id);
    }

    @Override
    public List<User> getAll() {
        Query<User> query = getCurrentSession().createQuery("from User", User.class);
        return query.getResultList();
    }

    @Override
    public User save(User object) {
        object.setModifiedAt(LocalDateTime.now());
        getCurrentSession().saveOrUpdate(Objects.requireNonNull(object));
        return object;
    }

    @Override
    public void delete(User object) {
        object.setDeletedAt(LocalDateTime.now());
        getCurrentSession().delete(Objects.requireNonNull(object));
    }

}