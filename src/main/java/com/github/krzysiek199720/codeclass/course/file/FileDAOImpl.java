package com.github.krzysiek199720.codeclass.course.file;

import com.github.krzysiek199720.codeclass.auth.user.User;
import com.github.krzysiek199720.codeclass.core.db.GenericDAO;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Objects;

@Repository
public class FileDAOImpl extends GenericDAO<File> implements FileDAO {

    @Override
    public List<File> getAddByCourse(Long courseId) {
        return getCurrentSession().createQuery("select f from File f inner join f.course c where c.id = :courseid", File.class)
                .setParameter("courseid", courseId).getResultList();
    }

    @Override
    public User getUserByFile(Long id) {
        Query<User> query =  getCurrentSession().createQuery("select cg.user from File f inner join f.course c inner join c.courseGroup cg where f.id = :fileid", User.class)
                .setParameter("fileid", id);

        User res;
        try{
            res = query.getSingleResult();
        } catch(NoResultException exc){
            return null;
        }

        return res;
    }

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
