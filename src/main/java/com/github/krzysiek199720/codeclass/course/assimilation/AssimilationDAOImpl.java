package com.github.krzysiek199720.codeclass.course.assimilation;

import com.github.krzysiek199720.codeclass.core.db.GenericDAO;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.Objects;

@Repository
public class AssimilationDAOImpl extends GenericDAO<Assimilation> implements AssimilationDAO {

    @Override
    public Assimilation getByCourseUser(Long courseId, Long userId) {
        Query<Assimilation> query = getCurrentSession().createQuery("from Assimilation where course.id = :courseid and user.id = :userid", Assimilation.class)
                .setParameter("courseid", courseId)
                .setParameter("userid", userId);

        Assimilation assimilation;
        try {
            assimilation = query.getSingleResult();
        }catch(NoResultException exc){
            return null;
        }
        return assimilation;
    }


    //----
    @Autowired
    public AssimilationDAOImpl(EntityManager entityManager){
        super(entityManager);
    }

    @Override
    public Assimilation getById(Long id) {
        return getCurrentSession().get(Assimilation.class, id);
    }

    @Override
    public Assimilation save(Assimilation object) {
        getCurrentSession().saveOrUpdate(Objects.requireNonNull(object));
        return object;
    }

    @Override
    public void delete(Assimilation object) {
        getCurrentSession().delete(Objects.requireNonNull(object));
    }

}
