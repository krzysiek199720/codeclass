package com.github.krzysiek199720.codeclass.course.coursedata;

import com.github.krzysiek199720.codeclass.core.db.GenericDAO;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Objects;

@Repository
public class DataImageDAOImpl extends GenericDAO<DataImage> implements DataImageDAO{

    @Override
    public DataImage get(Long courseId, String localId) {
        Query<DataImage> query = getCurrentSession().createQuery("select di from DataImage di where di.course.id = :courseid and di.localId like :localid", DataImage.class);
        query.setParameter("courseid", courseId);
        query.setParameter("localid", localId);

        DataImage dataImage;
        try{
            dataImage = query.getSingleResult();
        } catch(NoResultException exc){
            return null;
        }

        return dataImage;
    }

    @Override
    public List<DataImage> getAll(Long courseId) {
        Query<DataImage> query = getCurrentSession().createQuery("select di from DataImage di where di.course.id = :courseid", DataImage.class);
        query.setParameter("courseid", courseId);
        return query.getResultList();
    }

    //----
    @Autowired
    public DataImageDAOImpl(EntityManager entityManager){
        super(entityManager);
    }

    @Override
    public DataImage getById(Long id) {

        return getCurrentSession().get(DataImage.class, id);
    }

    @Override
    public DataImage save(DataImage object) {
        getCurrentSession().saveOrUpdate(Objects.requireNonNull(object));
        return object;
    }

    public List<DataImage> saveAll(List<DataImage> objects){
        for(DataImage cd : objects){
            getCurrentSession().save(cd);
        }
        return objects;
    }

    @Override
    public void delete(DataImage object) {
        getCurrentSession().delete(Objects.requireNonNull(object));
    }

}
