package com.github.krzysiek199720.codeclass.course.category;

import com.github.krzysiek199720.codeclass.core.db.GenericDAO;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;

@Repository
public class CategoryDAOImpl extends GenericDAO<Category> implements CategoryDAO {


    //----
    @Autowired
    public CategoryDAOImpl(EntityManager entityManager){
        super(entityManager);
    }

    @Override
    public Category getById(Long id) {
        return getCurrentSession().get(Category.class, id);
    }

    public List<Category> getAll() {
        Query<Category> query = getCurrentSession().createQuery("from Category", Category.class);
        return query.getResultList();
    }

    @Override
    public Category save(Category object) {
        getCurrentSession().saveOrUpdate(Objects.requireNonNull(object));
        return object;
    }

    @Override
    public void delete(Category object) {
        getCurrentSession().delete(Objects.requireNonNull(object));
    }
}
