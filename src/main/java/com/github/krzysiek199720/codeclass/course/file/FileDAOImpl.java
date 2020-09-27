package com.github.krzysiek199720.codeclass.course.file;

import com.github.krzysiek199720.codeclass.core.db.GenericDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Objects;

@Repository
public class FileDAOImpl extends GenericDAO<File> implements FileDAO {


    //----
    @Autowired
    public FileDAOImpl(EntityManager entityManager){
        super(entityManager);
    }

    @Override
    public File getById(Long id) {
        return getCurrentSession().get(File.class, id);
    }

    @Override
    public File save(File object) {
        getCurrentSession().saveOrUpdate(Objects.requireNonNull(object));
        return object;
    }

    @Override
    public void delete(File object) {
        getCurrentSession().delete(Objects.requireNonNull(object));
    }
}
