package com.github.krzysiek199720.codeclass.auth.accesstoken;

import com.github.krzysiek199720.codeclass.auth.user.User;
import com.github.krzysiek199720.codeclass.core.db.GenericDAO;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Repository
public class AccessTokenDAOImpl extends GenericDAO<AccessToken> implements AccessTokenDAO{


    public AccessToken findByToken(String token){
        Query<AccessToken> query = getCurrentSession().createQuery("from AccessToken where token = :token", AccessToken.class);
        query.setParameter("token", UUID.fromString(token));

        AccessToken accessToken;
        try{
            accessToken = query.getSingleResult();
        } catch(NoResultException exc){
            return null;
        }

        return accessToken;
    }

    public void deleteByUser(User user){
        getCurrentSession().createQuery("delete from AccessToken where user = :user")
                .setParameter("user", user)
                .executeUpdate();
    }

    //--------
    @Autowired
    public AccessTokenDAOImpl(EntityManager entityManager){
        super(entityManager);
    }

    @Override
    public AccessToken getById(Long id) {
        return getCurrentSession().get(AccessToken.class, id);
    }

    @Override
    public List<AccessToken> getAll() {
        Query<AccessToken> query = getCurrentSession().createQuery("from AccessToken order by id", AccessToken.class);
        return query.getResultList();
    }

    @Override
    public AccessToken save(AccessToken object) {
        getCurrentSession().saveOrUpdate(Objects.requireNonNull(object));
        return object;
    }

    @Override
    public void delete(AccessToken object) {
        getCurrentSession().delete(Objects.requireNonNull(object));
    }
}
