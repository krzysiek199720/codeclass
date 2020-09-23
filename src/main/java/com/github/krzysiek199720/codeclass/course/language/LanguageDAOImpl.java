package com.github.krzysiek199720.codeclass.course.language;

import com.github.krzysiek199720.codeclass.core.db.GenericDAO;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;

@Repository
public class LanguageDAOImpl extends GenericDAO<Language> implements LanguageDAO{


    //----
    @Autowired
    public LanguageDAOImpl(EntityManager entityManager){
        super(entityManager);
    }

    @Override
    public Language getById(Long id) {
        return getCurrentSession().get(Language.class, id);
    }

    public List<Language> getAll() {
        Query<Language> query = getCurrentSession().createQuery("from Language", Language.class);
        return query.getResultList();
    }

    @Override
    public Language save(Language object) {
        getCurrentSession().saveOrUpdate(Objects.requireNonNull(object));
        return object;
    }

    @Override
    public void delete(Language object) {
        getCurrentSession().delete(Objects.requireNonNull(object));
    }
}
