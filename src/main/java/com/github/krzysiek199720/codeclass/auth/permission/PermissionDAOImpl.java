package com.github.krzysiek199720.codeclass.auth.permission;

import com.github.krzysiek199720.codeclass.core.db.GenericDAO;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Repository
public class PermissionDAOImpl extends GenericDAO<Permission> implements PermissionDAO{


    public Permission getByValue(String value){
        Query<Permission> query = getCurrentSession().createQuery("from Permission where value LIKE :val", Permission.class);
        query.setParameter("val", value);

        Permission role;
        try{
            role = query.getSingleResult();
        } catch(NoResultException exc){
            return null;
        }

        return role;
    }

    public Set<Permission> getAllIn(Set<Long> ids){
        Query<Permission> query = getCurrentSession().createQuery("from Permission where id in :ids", Permission.class)
                .setParameterList("ids", ids);

        return new HashSet<>(query.getResultList());
    }

    //--------
    @Autowired
    public PermissionDAOImpl(EntityManager entityManager){
        super(entityManager);
    }

    @Override
    public Permission getById(Long id) {
        return getCurrentSession().get(Permission.class, id);
    }

    @Override
    public List<Permission> getAll() {
        Query<Permission> query = getCurrentSession().createQuery("from Permission order by id", Permission.class);
        return query.getResultList();
    }

    @Override
    public Permission save(Permission object) {
        getCurrentSession().saveOrUpdate(Objects.requireNonNull(object));
        return object;
    }

    @Override
    public void delete(Permission object) {
        getCurrentSession().delete(Objects.requireNonNull(object));
    }
}
