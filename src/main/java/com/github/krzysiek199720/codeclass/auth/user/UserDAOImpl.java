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
public class UserDAOImpl extends GenericDAO<User> implements UserDAO{

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

    public List<User> getAll(String searchQuery, Long roleId) {
        String baseQuery = "select u from User u where " +
                "(u.firstname like '%'||:searchQuery||'%' OR " +
                "u.lastname like '%'||:searchQuery||'%' OR " +
                "u.email like '%'||:searchQuery||'%'" +
                ") ";
        String roleQueryAdd = " AND u.role.id = :roleId";

        Query<User> query = null;

        if(roleId != null){
            query = getCurrentSession().createQuery(baseQuery + roleQueryAdd, User.class);
            query.setParameter("roleId", roleId);
        }else
            query = getCurrentSession().createQuery(baseQuery, User.class);

        query.setParameter("searchQuery", searchQuery);
        return query.getResultList();
    }

//----
    @Autowired
    public UserDAOImpl(EntityManager entityManager){
        super(entityManager);
    }

    @Override
    public User getById(Long id) {
        return getCurrentSession().get(User.class, id);
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
