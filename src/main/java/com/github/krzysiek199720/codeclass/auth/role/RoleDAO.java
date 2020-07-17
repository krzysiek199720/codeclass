package com.github.krzysiek199720.codeclass.auth.role;

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
public class RoleDAO extends GenericDAO<Role> {

    public Role findByName(String name){
        Query<Role> query = getCurrentSession().createQuery("from Role where name LIKE :name", Role.class);
        query.setParameter("name", name);

        Role role;
        try{
            role = query.getSingleResult();
        } catch(NoResultException exc){
            return null;
        }

        return role;
    }

    public List<RoleNameDTO> findAllNames(){
        Query<RoleNameDTO> query = getCurrentSession()
                .createQuery("select new com.github.krzysiek199720.codeclass.auth.role.RoleNameDTO(id, name) from Role order by id", RoleNameDTO.class);
        return query.getResultList();
    }

//----
    @Autowired
    public RoleDAO(EntityManager entityManager){
        super(entityManager);
    }

    @Override
    public Role getById(Long id) {
        return getCurrentSession().get(Role.class, id);
    }

    @Override
    public List<Role> getAll() {
        Query<Role> query = getCurrentSession().createQuery("from Role", Role.class);
        return query.getResultList();
    }

    @Override
    public Role save(Role object) {
        object.setModifiedAt(LocalDateTime.now());
        getCurrentSession().saveOrUpdate(Objects.requireNonNull(object));
        return object;
    }

    @Override
    public void delete(Role object) {
        object.setDeletedAt(LocalDateTime.now());
        getCurrentSession().delete(Objects.requireNonNull(object));
    }

}
